import re
import pandas as pd
import numpy as np
import csv
import warnings
warnings.filterwarnings("ignore", category=DeprecationWarning)

from sklearn import preprocessing
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.model_selection import cross_val_score
from sklearn.svm import SVC

from fastapi import FastAPI
from pydantic import BaseModel

from gtts import gTTS
import io, base64

# ------------------- Load model -------------------

training = pd.read_csv('Data/Training.csv')
testing = pd.read_csv('Data/Testing.csv')
cols = training.columns[:-1]

x = training[cols]
y = training['prognosis']

# Label encode target
le = preprocessing.LabelEncoder()
le.fit(y)
y_encoded = le.transform(y)

x_train, x_test, y_train, y_test = train_test_split(x, y_encoded, test_size=0.33, random_state=42)

clf = DecisionTreeClassifier()
clf.fit(x_train, y_train)

model_svm = SVC()
model_svm.fit(x_train, y_train)

# Prepare severity, description, precaution dicts
severityDictionary = {}
description_list = {}
precautionDictionary = {}

with open('MasterData/symptom_severity.csv') as f:
    reader = csv.reader(f)
    for row in reader:
        try:
            severityDictionary[row[0]] = int(row[1])
        except:
            pass

with open('MasterData/symptom_Description.csv') as f:
    reader = csv.reader(f)
    for row in reader:
        description_list[row[0]] = row[1]

with open('MasterData/symptom_precaution.csv') as f:
    reader = csv.reader(f)
    for row in reader:
        precautionDictionary[row[0]] = [row[1], row[2], row[3], row[4]]

# Symptoms dict for vectorization
symptoms_dict = {symptom: idx for idx, symptom in enumerate(x)}

# ------------------- Prediction function -------------------

def sec_predict(symptoms_exp):
    df = pd.read_csv('Data/Training.csv')
    X = df.iloc[:, :-1]
    y = df['prognosis']
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=20)
    rf_clf = DecisionTreeClassifier()
    rf_clf.fit(X_train, y_train)
    symptoms_dict_local = {symptom: index for index, symptom in enumerate(X)}
    input_vector = np.zeros(len(symptoms_dict_local))
    for item in symptoms_exp:
        if item in symptoms_dict_local:
            input_vector[symptoms_dict_local[item]] = 1
    return rf_clf.predict([input_vector])

def predict_disease(symptoms_input, num_days=1):
    # preprocess symptoms
    symptoms_input = [s.replace(' ', '_') for s in symptoms_input]

    # Decision tree prediction
    input_vector = np.zeros(len(symptoms_dict))
    for item in symptoms_input:
        if item in symptoms_dict:
            input_vector[symptoms_dict[item]] = 1
    clf_pred = clf.predict([input_vector])
    clf_pred_name = le.inverse_transform(clf_pred)[0]

    # Secondary prediction
    second_prediction = sec_predict(symptoms_input)

    # Severity calculation
    severity_sum = sum([severityDictionary.get(sym, 0) for sym in symptoms_input])
    severity_score = (severity_sum*num_days)/(len(symptoms_input)+1)
    advice = ("Consult a doctor!" if severity_score > 13 else "Mild symptoms, take precautions.")

    # Get description and precautions
    description_text = description_list.get(clf_pred_name, "")
    precautions_list = precautionDictionary.get(clf_pred_name, [])

    return {
        "predicted_disease": clf_pred_name,
        "alternative_prediction": second_prediction[0],
        "description": description_text,
        "precautions": precautions_list,
        "severity_score": severity_score,
        "advice": advice
    }

# ------------------- Multilingual support -------------------

from googletrans import Translator
translator = Translator()

def translate_text(text, target_lang='en'):
    if target_lang == 'en':
        return text
    result = translator.translate(text, dest=target_lang)
    return result.text


# ------------------- Text-to-Speech -------------------

def tts_base64(text, lang='en'):
    mp3_fp = io.BytesIO()
    tts = gTTS(text=text, lang=lang)
    tts.write_to_fp(mp3_fp)
    mp3_fp.seek(0)
    return base64.b64encode(mp3_fp.read()).decode('utf-8')

# ------------------- FastAPI -------------------

app = FastAPI()

class SymptomsRequest(BaseModel):
    symptoms: list
    num_days: int = 1
    lang: str = 'en'
    want_audio: bool = False

@app.post("/predict")
def predict(req: SymptomsRequest):
    res = predict_disease(req.symptoms, req.num_days)

    # Translate description & advice
    res['description'] = translate_text(res['description'], req.lang)
    res['advice'] = translate_text(res['advice'], req.lang)

    # Optional audio
    if req.want_audio:
        res['audio_base64'] = tts_base64(res['advice'], req.lang)

    return res
