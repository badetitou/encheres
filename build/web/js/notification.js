function getParameterByName(name, url) {
    if (!url) {
        url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}


var ok = getParameterByName('ok');
if(ok === 'ko'){
    var message = getParameterByName('description');
    if (message != null){
        Materialize.toast(message, 10000, 'rounded')
    } else {
        Materialize.toast('Erreur non repertoriée... Désolé ^^', 10000, 'rounded')
    }
} else if (ok === 'ok'){
    var message = getParameterByName('description');
    if (message != null){
        Materialize.toast(message, 10000, 'rounded')
    } else {
        Materialize.toast('Tout c\'est bien passée !!', 10000, 'rounded')
    }
}