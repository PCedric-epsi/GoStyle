from app_api import *
import json


''' Il y a 1 assert pour vérifier que l'on retourne une réponse 200
    Et il y a 1 assert pour vérifier la compatibilité des noms de codes avec les values de codes, 
    il y a 9 noms de code et 9 values de code, donc il y aura 81 tests pour vérifier que 1 code à uniquement 1 nom et 1 value.    
    Au total les 2 asserts seront exécutés 81 fois chacuns donc on doit avoir 162 assert testés à la fin du test'''


tabCode = ["SUPER CODE", "TERA CODE", "GIGA CODE", "PETA CODE", "MEGA CODE", "ZETTA CODE", "YOTTA CODE", "EXA CODE", "NOEL CODE"]
tabValue = [30, 60 , 50, 70, 40, 90, 100, 80, 25]

def test_code():
    for i, code in enumerate(tabCode):
        for j, value in enumerate(tabValue):
            response = app.test_client().get('/api/code/' + code)
            
            data = json.loads(response.get_data(as_text=True))

            assert response.status_code == 200
            assert (data['value'] == value) == (i == j)