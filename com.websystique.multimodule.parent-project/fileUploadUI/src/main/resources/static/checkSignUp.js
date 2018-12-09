function myGetElementById(idElemento) {

	// elemento da restituire
	var elemento;

	// se esiste il metodo getElementById questo if sara'� 
	// diverso da false, null o undefined
	// e sara'� quindi considerato valido, come un true
	if ( document.getElementById )
		elemento = document.getElementById(idElemento);

	// altrimenti e' necessario usare un vecchio sistema
	else
		elemento = document.all[idElemento];

	// restituzione elemento
	return elemento;

} // myGetElementById()



/**
 * from http://www.e-time.it/topics/34-ajax/8-Richiamare%20l%5C'oggetto%20XmlHttpRequest
 */ 
function myGetXmlHttpRequest() {

	// variabili 
	var 
		// risultato 
		xhr = false,
		// opzioni activeX dal pi� nuovo al pi� vecchio
		activeXoptions = new Array( "Microsoft.XmlHttp", "MSXML4.XmlHttp", "MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp" );

	// primo tentativo come oggetto nativo
	try { 
		xhr = new XMLHttpRequest(); 
	} 
	catch (e) { 
		// non facciamo niente... semplicemente proviamo un altro modo
	}

	// successivi tentativi come oggetto activeX dal piu' al meno recente
	if ( ! xhr ) {
		var created = false; 
		for ( var i = 0 ; i < activeXoptions.length && !created ; i++ ) {
			try {
				// tentativo di creazione
				xhr = new ActiveXObject( activeXoptions[i] );
				// se la creazione non fallisce il codice del blocco try continua a essere eseguito
				created = true;
			} 
			catch (e) { 
				// non facciamo niente... semplicemente proviamo un altro modo
			} 
		} // for ( MSXML options )
	} // if ( ! nativo )

	// restituzione del risultato, eventualmente ancora false se il browser non supporta AJAX
	return xhr;

} // myGetXmlHttpRequest()



/*
 * Funzione per recuperare per nome l'elemento figlio di un elemento dato
 *
 * Non usa l'id (che deve essere unico nel DOM) ma il name 
 * per lasciare la possibilita' di avere piu' nodi con lo stesso name
 * ma figli di elementi diversi.
 *
 * Ad esempio per collocare piu' immagini di attesa nel documento, 
 * in caso di piu' richieste AJAX contemporanee
 */
function myGetChildByName( theElement, name ) {
	
	// analisi alla ricerca del nodo voluto
	if ( theElement.childNodes ){
		for ( var i = 0 ; i < theElement.childNodes.length ; i++ ){
			if ( theElement.childNodes[i].nodeName == name )
				return theElement.childNodes[i];
		}
	}

}// myGetChildByName()

function resetDiv() {
	myGetElementById('userErr').innerHTML = "";
	myGetElementById('passErr').innerHTML = "";
	myGetElementById('emailErr').innerHTML = "";
}

function resetCpass(cpass) {
	cpass.value = "";
	myGetElementById('passErr').innerHTML = "";
}

function controlloPassword(element1, element2) {
	if( element1.value != element2.value){
		myGetElementById('passErr').innerHTML = "The passwords are not equals.";
		return false;
	}else{
		myGetElementById('passErr').innerHTML = "";
		return true;
	}
}

function validEmail(element) {
	
	var result = false;
	if ( element.value != "" ) {
		var tempstr = new String(element.value);
		var aindex = tempstr.indexOf("@");
		if (aindex > 0 ) {
			var pindex = tempstr.indexOf(".",aindex);
	        if ( (pindex > aindex+1) && (tempstr.length > pindex+1) ) {
	        	//controlloEmail(element);
	        	myGetElementById('emailErr').innerHTML = "";
	        	return;
				result = true;
			} else {
				result = false;
			}
		}
	}else{
		result = true;
		myGetElementById('emailErr').innerHTML = "";
	}
	if(!result){
		myGetElementById('emailErr').innerHTML = "Please enter an email with a valid address format";
	}
}
/*
function controlloEmail(element) {
	var xhr = myGetXmlHttpRequest();
	if( xhr ){
		xhr.onreadystatechange = function() { callBack2(xhr, element); };
		try{
			xhr.open("post","checkRegistration",false);
		}catch(e){
			alert(e);
		}
		xhr.setRequestHeader("connection","close");
		xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
		xhr.send("email="+element.value);
	}
}*/
/*
function callBack2(xhr, element) {
	if( xhr.readyState == 4){
		if( xhr.status == 200){
			if(xhr.responseText == 'true'){
				myGetElementById('emailErr').innerHTML = "email already exist";
			}else{	
				myGetElementById('emailErr').innerHTML= "";
			}	
		}
	}
}*/
function checkAll() {
	var form = myGetElementById("signupForm");
	//controlloUser(form.username);
	validEmail(form.email);
}