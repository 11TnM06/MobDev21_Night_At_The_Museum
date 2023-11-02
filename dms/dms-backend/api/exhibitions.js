module.exports = function (app, db, storage) {
    app.get('/exhibitions/list', async (req, res) => {
        try {
            var list = await db.collection('exhibitions').orderBy('name', 'asc').get();
            list.docs.sort((a, b) => {
                return b.data().date - a.data().date;
            });
            res.send({
                status: "success", message: list.docs.map(doc => {
                    var item = doc.data();
                    item.id = doc.id;
                    return item;
                })
            });
        } catch (e) {
            res.send({ status: "error", message: e });
        }
    });


    app.post('/exhibitions/add', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('exhibitions').add(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }

    });

    app.post('/exhibitions/update', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('exhibitions').doc(item.id).update(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });

    app.post('/exhibitions/delete', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('exhibitions').doc(item.id).delete();
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });
}