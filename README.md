
#### Json-Server and Json-Server-Aut installation
fake REST API with a fake authentication & authorization flow
```agsl
npm install -D json-server json-server-auth
```

#### Json-Server start
```
cd json-server-db
json-server-auth db.json -r routes.json
```

#### Allure report
```
allure generate --clean
allure open
```
