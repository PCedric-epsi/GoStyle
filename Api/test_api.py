from app_api import *
import json
import pytest

code = "SUPER CODE"

@pytest.mark.parametrize("x", ["SUPER CODE", "TERA CODE", "GIGA CODE", "PETA CODE", "MEGA CODE", "ZETTA CODE", "YOTTA CODE", "EXA CODE", "NOEL CODE"])
@pytest.mark.parametrize("y", [30, 60 , 50, 70, 40, 90, 100, 80, 25])
def test_code(x, y):
    response = app.test_client().get('/api/code/' + x)
    
    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['value'] == y

