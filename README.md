# mortgage
API позволяет взаимодействовать с покупателями, продавцами и заявками на ипотеку
Для хранения используется бд h2
для создания сущности отправляется http put запрос с данными в json формате на:
- /clients с полями name и passportData (10 символов без пробелов)
- /sellers с полями name, personalData (10 символов без пробелов) и sellerType (COMPANY или SALESMAN)
- /credits с полями creditAmount (не менее ста тысяч), totalAmount (не менее ста десяти тысяч), creditRate, 
  years (не более 50), objectOfCredit, объектов client и seller (есть возможность отправить объекты в теле запроса и 
  отправить id параметрами запроса)
для получения записей отправляется get запрос на:
- /clients для получения всех клиентов, для получения конкретного клиента необходим запрос /clients/id
  Аналогичные правила используются для /sellers и /credits
Для редактирования записей отправляется put запрос с полями необходимыми для редактирования на:
  - /clients/id, /sellers/id, и /credits/id. Для изменения покупателя и продавца в кредите в качестве параметра 
  передается новый id
Для удаления записей отправляется delete запрос с на:
  - /clients/id, /sellers/id, и /credits/id

Входные данные:
String jsonInputClient = "{"name": "Client", "passportData": "1233455678"}";
String jsonInputSeller = "{"name": "Seller Company", "personalData": "7704407589", "sellerType": "COMPANY"}";
String jsonInputSeller = "{"name": "Seller salesman", "personalData": "7704407588", "sellerType": "SALESMAN"}";
String jsonInputCreditWithId = "{"creditAmount": "1000000", "totalAmount": "1500000", "creditRate": "-1", 
"years": "10", "objectOfCredit": "Квартира в новостройке"}";
String jsonInputCreditByJSON = "{"creditAmount": "1000000", "totalAmount": "1500000", "creditRate": "-1", "years": "10",
"objectOfCredit": "Квартира в новостройке", "client":{ "name": "gfd", "passportData": "1234567890"},
"seller":{ "name": "gfd", "personalData": "7704407589", "sellerType": "COMPANY"}}";

Для создания заявки есть 2 способа:
  создать клиента и продавца и в ресте для создания заявки в качестве параметров указать их id 
  передать данные клиента и продавца в самом ресте заявки
  Также есть возможность реализации смешанного запроса, т.е. отправить Id клиента и в самом запросе данные продавца и наоборот