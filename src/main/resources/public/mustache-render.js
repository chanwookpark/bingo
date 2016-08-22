function render(template, model, url) {
    var json = convertToJsonObject(model);

    console.log("Template: " + template + "\nJSON: " + json);

    return Mustache.render(template, json);
}

// thanks to https://github.com/sdeleuze/spring-react-isomorphic/blob/master/src/main/resources/static/render.js
function convertToJsonObject(model) {
    var o = {};
    for (var k in model) {
        if (model[k] instanceof Java.type("java.lang.Iterable")) {
            o[k] = Java.from(model[k]);
        } else {
            o[k] = model[k];
        }
    }
    return o;
}