const STORAGE_LINK = process.env.STORAGE_LINK;
const crypto = require('node:crypto');
const multer = require("multer");
const upload = multer({ dest: "uploads/" });
const fs = require('fs');

module.exports = function (app, db, storage) {
    app.get('/items/list', async (req, res) => {
        try {
            var list = await db.collection('items').orderBy('name', 'asc').get();
            res.send({
                status: "success", message: list.docs.map(doc => {
                    var item = doc.data();
                    item.id = doc.id;
                    return item;
                })
            });
        } catch (e) {
            console.log(e)
            res.send({ status: "error", message: e });
        }
    });


    app.post('/items/add', upload.single('file'), async (req, res) => {
        
        if (req.file == undefined) {
            res.send({ status: "error", message: "No file uploaded" });
            return;
        }
        var model = req.file;
        var model_id = model.filename + '.glb';

        try {
            var item = JSON.parse(req.body.requests);
        } catch (e) {
            res.send({ status: "error", message: "No payload" });
            return;
        }
        try {
            const bucket = storage.bucket(STORAGE_LINK);
            console.log("this is model id: ", model_id);
            console.log("this is model path: ", model.path);
            const data = await bucket.upload(model.path, {
                destination: model_id,
                metadata: {
                    contentType: model.mimetype
                }
            });

            item.model3d = `https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/${model_id}?alt=media`
            item.model3d_id = model_id;
            var result = await db.collection('items').add(item);
            fs.rmSync(model.path);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            console.log(e)
            res.send({ status: "error", message: e });
        }
    });


    app.post('/items/update', upload.single('file'), async (req, res) => {
        if (req.file == undefined) {
            res.send({ status: "error", message: "No file uploaded" });
            return;
        }
        var model = req.file;
        var model_id = model.filename + ".glb";
        var item = JSON.parse(req.body.requests);
        try {
            var result = await db.collection('items').doc(item.id).get(item.id);
            result = result.data();
            await storage.bucket(STORAGE_LINK).file(result.model3d_id).delete();
            const bucket = storage.bucket(STORAGE_LINK);
            const data = await bucket.upload(model.path, {
                destination: model_id,
                metadata: {
                    contentType: model.mimetype
                }
            });
            item.model3d = `https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/${model_id}?alt=media`
            item.model3d_id = model_id;
            var result = await db.collection('items').doc(item.id).update(item);
            fs.rmSync(model.path);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            console.log(e);
            res.send({ status: "error", message: e });
        }
    });


    app.post('/items/delete', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('items').doc(item.id).get(item.id);
            result = result.data();
            await storage.bucket(STORAGE_LINK).file(result.model3d_id).delete();
            await db.collection('items').doc(item.id).delete();
            res.send({ status: "success" });
        }
        catch (e) {
            console.log(e)
            res.send({ status: "error", message: e });
        }
    });

}