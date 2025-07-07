
from ili2c import Ili2c
import os
import tempfile

TEST_DATA_PATH = "ili2c/tests/data/"

def test_create_ilismetas16_ok():
    with tempfile.TemporaryDirectory() as tmpdir:
        print("Temp dir path:", tmpdir)
        xtf_file = os.path.join(tmpdir, 'SO_ARP_SEin_Konfiguration_20250116.xtf')
        result = Ili2c.create_ilismetas16(TEST_DATA_PATH+"SO_ARP_SEin_Konfiguration_20250116.ili", xtf_file)
        assert result == True

        with open(xtf_file, 'r', encoding='utf-8') as f:
            file_content = f.read()

        search_str = '<IlisMeta16:Role ili:tid="SO_ARP_SEin_Konfiguration_20250115.Grundlagen.Thema_Objektinfo.Thema_R">'
        assert search_str in file_content