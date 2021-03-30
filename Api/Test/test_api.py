from app_api import *
import json
import pytest

''' test paramétré
    x prends le nom du code
    y prends la value du code
    il y a 9 noms de code et 9 values de code, onc il y aura 81 tests pour vérifier que 1 code à uniquement 1 nom et 1 value
    donc on doit retourner 72 test failed et 9 test success'''
@pytest.mark.parametrize("x", ["SUPER CODE", "TERA CODE", "GIGA CODE", "PETA CODE", "MEGA CODE", "ZETTA CODE", "YOTTA CODE", "EXA CODE", "NOEL CODE"])
@pytest.mark.parametrize("y", [30, 60 , 50, 70, 40, 90, 100, 80, 25])
def test_code(x, y):
    response = app.test_client().get('/api/code/' + x)
    
    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['value'] == y