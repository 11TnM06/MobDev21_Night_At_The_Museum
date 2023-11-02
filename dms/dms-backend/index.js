require('dotenv').config();

const express = require('express');
const { db, storage } = require('./firebase');
const bodyParser = require("body-parser")
const cors = require("cors")

const app = express();
const port = process.env.PORT || 3001;

app.use(cors({
    origin: "http://localhost:3000",
    credentials: true
}))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }))

require('./api/items.js')(app, db, storage);
require('./api/collections.js')(app, db, storage);
require('./api/exhibitions.js')(app, db, storage);
require('./api/stories.js')(app, db, storage);

app.listen(port, () => {
    console.log(`[App] Listening at port:${port}`);
});