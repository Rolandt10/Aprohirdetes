# Apróhirdetés alkalmazás

Egy apróhirdetés a következő tulajdonságokkal rendelkezik: cím, ár, leírás, kategória, 
létrehozás  dátuma,  telefonszám,  e-mail  cím,  helység.  Amikor  a  felhasználó  megnyitja  az 
alkalmazást, láthatja az éppen elérhető hirdetéseket, melyeket tud rendezni ár, ill. a létrehozás 
dátuma  szerint,  valamint  tud  köztük  keresni  kategória  és cím  szerint, továbbá tud szűrni ár 
szerint.  A  felhasználó  miután  bejelentkezett,  tud  létrehozni  saját  hirdetést,  a  meglévőket 
módosítani és törölni, illetve a mások által létrehozott hirdetéseket elmenteni. Egy 
apróhirdetés létrehozásához a fentebb  említett tulajdonságokat kell  megadni, kivéve a 
létrehozás  dátumát.  Módosításnál  szintén  ugyanezeket  lehet  megváltoztatni.  A  mentett 
hirdetések  a  backenden  kerülnek  tárolásra,  így,  ha  egy  mentett  hirdetést  törölnek,  akkor  a 
hirdetés törlésre kerül a felhasználó mentései közül. A hirdetések tárolásához, ill. a 
felhasználó azonosításához Firebase-t használok.
