# User Management API

## Descrizione

Questa è un'applicazione Java che espone una REST API per la gestione di utenti (entità `User`). 
L'applicazione consente di eseguire operazioni di **creazione**, **lettura**, **aggiornamento** e **cancellazione** degli utenti, oltre a offrire una funzionalità di **ricerca utenti filtrabile per nome e/o cognome**.

## Tecnologie Utilizzate

- Java (versione 21)
- Framework: Spring Boot
- Database: SQLite 3
- Gestione del progetto: Git

## Funzionalità

### Entità `User`

L'entità `User` è composta dai seguenti campi:

- `id` (Integer): identificativo univoco
- `name` (String)
- `surname` (String)
- `email` (String)
- `address` (String)

### Endpoints REST

| Metodo | Endpoint        | Descrizione                     |
|--------|-----------------|---------------------------------|
| GET    | `/users`        | Restituisce tutti gli utenti    |
| GET    | `/users/{id}`   | Restituisce un utente per ID    |
| POST   | `/users`        | Crea un nuovo utente            |
| PUT    | `/users/{id}`   | Aggiorna un utente esistente    |
| DELETE | `/users/{id}`   | Elimina un utente               |
| GET    | `/users/search` | Ricerca utenti per nome/cognome |
| POST   | `/users/csv`    | Caricamento utenti da file csv  |


#### Esempio ricerca

```http
GET /users/search?nome=Mario&cognome=Rossi
