/* generated javascript */
var skin = 'monobook';
var stylepath = '/skins-1.5';

/* MediaWiki:Common.js */
/* Il codice JavaScript inserito qui viene caricato da ciascuna pagina, per tutti gli utenti. */

/* Serve per "sabotare" www.wikipedia.it e tutti gli altri siti che ci includono in frame. "Salta fuori" dai frame.
    Va alla pagina del toolserver il cui contenuto è preso dalla pagina [[Utente:Pietrodn/www.wikipedia.it]], modificabile solo dagli admin.
    Poi reindirizza a http://it.wikipedia.org */
if (wgTitle == "Pagina principale" && top !=self) top.location='http://toolserver.org/~pietrodn/wikipedia_redirect.php';

// BEGIN Enable multiple onload functions

// setup onload functions this way:
// aOnloadFunctions[aOnloadFunctions.length] = function_name; // without brackets!

if (!window.aOnloadFunctions) {
  var aOnloadFunctions = new Array();
}

window.onload = function() {
  if (window.aOnloadFunctions) {
    for (var _i=0; _i<aOnloadFunctions.length; _i++) {
      aOnloadFunctions[_i]();
    }
  }
}

// END Enable multiple onload functions


function addLoadEvent(func) 
{
  if (window.addEventListener) 
    window.addEventListener("load", func, false);
  else if (window.attachEvent) 
    window.attachEvent("onload", func);
}

/*** RICERCA ***/
/** 
*******************************************************
*
*  Descrizione: Cambia Speciale:Ricerca per usare un menu' a
*  tendina, con come default il motore interno di MediaWiki
*
*  Creato e gestito da: [[:en:User:Gracenotes]]
*  Importato in it.wiki da: [[Utente:Jalo]]
*/
 
 if (wgPageName == "Speciale:Ricerca") {
         var searchEngines = [];
         addOnloadHook(SpecialSearchEnhanced);
 }
 
 function SpecialSearchEnhanced() {
         var createOption = function(site, action, mainQ, addQ, addV) {
                 var opt = document.createElement('option');
                 opt.appendChild(document.createTextNode(site));
                 searchEngines[searchEngines.length] = [action, mainQ, addQ, addV];
                 return opt;
         }
         if (document.forms['powersearch'])
             var searchForm = document.forms['powersearch'];
         if (document.forms['search'])
             var searchForm = document.forms['search'];

         if (searchForm.lsearchbox) {
             var searchBox = searchForm.lsearchbox;
         } else {
             var searchBox = searchForm.search;
         }

         var selectBox = document.createElement('select');
         selectBox.id = 'searchEngine';
         searchForm.onsubmit = function() {
                 var optSelected = searchEngines[document.getElementById('searchEngine').selectedIndex];
                 searchForm.action = optSelected[0];
                 searchBox.name = optSelected[1];
                 searchForm.title.value = optSelected[3];
                 searchForm.title.name = optSelected[2];
         }
         selectBox.appendChild(createOption('MediaWiki', wgScriptPath + '/index.php', 'search', 'title', 'Speciale:Ricerca'));
         selectBox.appendChild(createOption('Google', 'http://www.google.com/search', 'q', 'sitesearch', 'it.wikipedia.org'));
         selectBox.appendChild(createOption('Yahoo', 'http://search.yahoo.com/search', 'p', 'vs', 'it.wikipedia.org'));
         selectBox.appendChild(createOption('Windows Live', 'http://search.live.com/results.aspx', 'q', 'q1', 'site:http://it.wikipedia.org'));
         selectBox.appendChild(createOption('Wikiwix', 'http://it.wikiwix.com/', 'action', 'lang', 'it'));
         selectBox.appendChild(createOption('Exalead', 'http://www.exalead.com/wikipedia/results', 'q', 'language', 'it'));

         searchBox.style.marginLeft = '0px';
		 
		 // 'searchText' is the firt search text. 'powerSearchText' is the second, used only if the first is not existent (e.g.: not yet done any search)
		 buttonSearch=document.getElementById('searchText');
		 if (buttonSearch)
		   buttonSearch.parentNode.insertBefore(selectBox, buttonSearch.nextSibling);
		 else
		 {
		   buttonSearch=document.getElementById('powerSearchText');
		   buttonSearch.parentNode.insertBefore(selectBox, buttonSearch.nextSibling);
		 }
}

// Serve per far funzionare il Cassetto2 con IE6.
if (window.attachEvent && !window.XMLHttpRequest) window.attachEvent("onload", function() {
        var x=document.getElementById("bodyContent").getElementsByTagName("div");
        for (var i=0; i<x.length; i++) {
                if (x[i].className != "HopFrame") continue;
                var y=x[i].getElementsByTagName("div"); var j;
                for (j=0; j<y.length; j++)
                        if (y[j].className == "HopContent") {x[i].hopContent = y[j]; break;}
                if (j<y.length) {
                        x[i].onmouseover=function() { this.hopContent.style.display="block"; };
                        x[i].onmouseout=function() { this.hopContent.style.display="none"; };
                }
        }
});

// BEGIN Dynamic Navigation Bars


/* Test if an element has a certain class **************************************
 *
 * Description: Uses regular expressions and caching for better performance.
 * Maintainers: User:Mike Dillon, User:R. Koot, User:SG
 */

var hasClass = (function () {
    var reCache = {};
    return function (element, className) {
        return (reCache[className] ? reCache[className] : (reCache[className] = new RegExp("(?:\\s|^)" + className + "(?:\\s|$)"))).test(element.className);
    };
})();


// set up the words in your language
var NavigationBarHide = '▲ nascondi';
var NavigationBarShow = '▼ espandi';

// set up max count of Navigation Bars on page,
// if there are more, all will be hidden
// NavigationBarShowDefault = 0; // all bars will be hidden
// NavigationBarShowDefault = 1; // on pages with more than 1 bar all bars will be hidden
var NavigationBarShowDefault = 0;


// shows and hides content and picture (if available) of navigation bars
// Parameters:
//     indexNavigationBar: the index of navigation bar to be toggled
function toggleNavigationBar(indexNavigationBar)
{
   var NavToggle = document.getElementById("NavToggle" + indexNavigationBar);
   var NavFrame = document.getElementById("NavFrame" + indexNavigationBar);

   if (!NavFrame || !NavToggle) {
       return false;
   }

   // if shown now
   if (NavToggle.firstChild.data == NavigationBarHide) {
       for (
               var NavChild = NavFrame.firstChild;
               NavChild != null;
               NavChild = NavChild.nextSibling
           ) {
           if (hasClass(NavChild, 'NavPic')) {
               NavChild.style.display = 'none';
           }
           if (hasClass(NavChild, 'NavContent')) {
               NavChild.style.display = 'none';
           }
           if (hasClass(NavChild, 'NavToggle')) {
               NavChild.firstChild.data = NavigationBarShow;
           }
       }

   // if hidden now
   } else if (NavToggle.firstChild.data == NavigationBarShow) {
       for (
               var NavChild = NavFrame.firstChild;
               NavChild != null;
               NavChild = NavChild.nextSibling
           ) {
           if (hasClass(NavChild, 'NavPic')) {
               NavChild.style.display = 'block';
           }
           if (hasClass(NavChild, 'NavContent')) {
               NavChild.style.display = 'block';
           }
           if (hasClass(NavChild, 'NavToggle')) {
               NavChild.firstChild.data = NavigationBarHide;
           }
       }
   }
}

// adds show/hide-button to navigation bars
function createNavigationBarToggleButton()
{
   var indexNavigationBar = 0;
   // iterate over all < div >-elements
   for(
           var i=0; 
           NavFrame = document.getElementsByTagName("div")[i]; 
           i++
       ) {
       // if found a navigation bar
       if (hasClass(NavFrame, 'NavFrame')) {

           indexNavigationBar++;
           var NavToggle = document.createElement("a");
           NavToggle.className = 'NavToggle';
           NavToggle.setAttribute('id', 'NavToggle' + indexNavigationBar);
           NavToggle.setAttribute('href', 'javascript:toggleNavigationBar(' + indexNavigationBar + ');');

           var NavToggleText = document.createTextNode(NavigationBarHide);
           NavToggle.appendChild(NavToggleText);

           // add NavToggle-Button as first div-element 
           // in < div class="NavFrame" >
           NavFrame.insertBefore(
               NavToggle,
               NavFrame.firstChild
           );
           NavFrame.setAttribute('id', 'NavFrame' + indexNavigationBar);
       }
   }
   // if more Navigation Bars found than Default: hide all
   if (NavigationBarShowDefault < indexNavigationBar) {
       for(
               var i=1; 
               i<=indexNavigationBar; 
               i++
       ) {
           toggleNavigationBar(i);
       }
   }

}

aOnloadFunctions[aOnloadFunctions.length] = createNavigationBarToggleButton;

// END Dynamic Navigation Bars


/** Collapsible tables *********************************************************
 *
 *  Taken from http://en.wikipedia.org/wiki/MediaWiki:Common.js
 *  Description: Allows tables to be collapsed, showing only the header. See
 *               Wikipedia:NavFrame.
 *  Maintainers: User:R. Koot
 */

var autoCollapse = 2;
var collapseCaption = "▲ nascondi";
var expandCaption = "▼ espandi";

function collapseTable( tableIndex )
{
    var Button = document.getElementById( "collapseButton" + tableIndex );
    var Table = document.getElementById( "collapsibleTable" + tableIndex );

    if ( !Table || !Button ) {
        return false;
    }

    var Rows = Table.getElementsByTagName( "tr" ); 

    if ( Button.firstChild.data == collapseCaption ) {
        for ( var i = 1; i < Rows.length; i++ ) {
            Rows[i].style.display = "none";
        }
        Button.firstChild.data = expandCaption;
    } else {
        for ( var i = 1; i < Rows.length; i++ ) {
            Rows[i].style.display = Rows[0].style.display;
        }
        Button.firstChild.data = collapseCaption;
    }
}

function createCollapseButtons()
{
    var tableIndex = 0;
    var NavigationBoxes = new Object();
    var Tables = document.getElementsByTagName( "table" );

    for ( var i = 0; i < Tables.length; i++ ) {
        if ( hasClass( Tables[i], "collapsible" ) ) {
            NavigationBoxes[ tableIndex ] = Tables[i];
            Tables[i].setAttribute( "id", "collapsibleTable" + tableIndex );

            var Button     = document.createElement( "span" );
            var ButtonLink = document.createElement( "a" );
            var ButtonText = document.createTextNode( collapseCaption );

            Button.style.styleFloat = "right";
            Button.style.cssFloat = "right";
            Button.style.fontWeight = "normal";
            Button.style.textAlign = "right";
            Button.style.width = "7em";

            ButtonLink.setAttribute( "id", "collapseButton" + tableIndex );
            ButtonLink.setAttribute( "href", "javascript:collapseTable(" + tableIndex + ");" );
            ButtonLink.appendChild( ButtonText );

            Button.appendChild( document.createTextNode( "[" ) );
            Button.appendChild( ButtonLink );
            Button.appendChild( document.createTextNode( "]" ) );

            var Header = Tables[i].getElementsByTagName( "tr" )[0].getElementsByTagName( "th" )[0];
            /* only add button and increment count if there is a header row to work with */
            if (Header) {
                Header.insertBefore( Button, Header.childNodes[0] );
                tableIndex++;
            }
        }
    }

    for ( var i = 0;  i < tableIndex; i++ ) {
        if ( hasClass( NavigationBoxes[i], "collapsed" ) || ( tableIndex >= autoCollapse && hasClass( NavigationBoxes[i], "autocollapse" ) ) ) {
            collapseTable( i );
        }
    }
}

addOnloadHook( createCollapseButtons );

//END Collapsible tables

//HIDDENCAT (mostra le categorie nascoste). Scippato ai francesi
function addClass(node, className) {
    if (hasClass(node, className)) {
        return false;
    }
    node.className += ' '+ className;
    return true;
}

function eregReplace(search, replace, subject) {
    return subject.replace(new RegExp(search,'g'), replace);
}

function removeClass(node, className) {
  if (!hasClass(node, className)) {
    return false;
  }
  node.className = eregReplace('(^|\\s+)'+ className +'($|\\s+)', ' ', node.className);
  return true;
}

function isClass(element, classe) {
    return hasClass(element, classe);
}

function hiddencat()
{
 var cl = document.getElementById('catlinks');           if(!cl) return;
 var hc = document.getElementById('mw-hidden-catlinks'); if(!hc) return;
 var nc = document.getElementById('mw-normal-catlinks');
 if(!nc)
 {
  removeClass(cl, 'catlinks-allhidden');
  addClass(cl, 'catlinks-allhidden-mostra');
  var ahc = '<div id="mw-normal-catlinks"><a href="/wiki/Categoria:Categorie" title="Categoria:Categorie">Categorie</a> : <span dir="ltr"><a onclick="javascript:toggleHiddenCats();" id="mw-hidden-cats-link" style="cursor:pointer; color:#002BB8;" title="Questa voce contiene categorie nascoste">[<span style="font-style:italic;">altre</span>]</a></span></div>';
  document.getElementById('catlinks').innerHTML = ahc + cl.innerHTML;
 }
 else if( isClass(hc, 'mw-hidden-cats-hidden') )
 {
  var ahc = ' | <a onclick="javascript:toggleHiddenCats();" id="mw-hidden-cats-link" style="cursor:pointer; color:#002BB8;" title="Questa voce contiene categorie nascoste">[<span style="font-style:italic;">altre</span>]</a>';
  document.getElementById('mw-normal-catlinks').innerHTML += ahc;
 }
}
function toggleHiddenCats()
{
 var hc = document.getElementById('mw-hidden-catlinks');
 if( isClass(hc, 'mw-hidden-cats-hidden') )
 {
  removeClass(hc, 'mw-hidden-cats-hidden');
  addClass(hc, 'mw-hidden-cat-user-shown');
  document.getElementById('mw-hidden-cats-link').innerHTML = '[<span style="font-style:italic;">nascondi</span>]';
 }
 else
 {
  removeClass(hc, 'mw-hidden-cat-user-shown');
  addClass(hc, 'mw-hidden-cats-hidden');
  document.getElementById('mw-hidden-cats-link').innerHTML = '[<span style="font-style:italic;">altre</span>]';
 }
}
 
addOnloadHook(hiddencat);

/* Layout pagina di modifica */
addLoadEvent ( function ()
{
  if (wgAction == "edit")
  {
    /* Allarga l'edittools a tutta pagina */
    document.getElementById("Standard").style.width = "100%";
  }
})

/* Aggiunge il testo delle note come tooltip delle note stesse */
addOnloadHook ( function ()
{
  sups = document.getElementsByTagName("sup");
  for (i=0; i<sups.length; i++)
  {
    note_id = sups[i].childNodes[0].href;
    if (note_id && (note_id.indexOf("#") != -1))
    {
      note_id = document.getElementById(note_id.substr(note_id.indexOf("#")+1));
      if (note_id)
        if (document.all) 
        { 
            sups[i].title = note_id.innerText; 
            sups[i].childNodes[0].title = note_id.innerText; 
        } 
        else 
        { 
            sups[i].title = note_id.textContent; 
       }
    }
  }
})

/* Avvisa quando si tenta di creare una voce con titolo non valido  ("Scrivi qui il titolo")*/
addOnloadHook ( function ()
{
  if (wgTitle == "Scrivi qui il titolo" && wgAction == "edit")
  {
    dopoDi = document.getElementById("contentSub");
	if (!dopoDi) return;
    var daInserire = document.createElement('div');
    daInserire.innerHTML = "<b>Stai creando una pagina senza specificare un titolo valido per la voce. Per favore torna indietro e inserisci un titolo valido.</b>";
	daInserire.className = "toccolours itwiki_template_avviso rad";
	daInserire.style.marginBottom = "0.5em";
	daInserire.style.background = "#FC9";
	daInserire.style.borderColor = "red";
	daInserire.style.textAlign = "center";
	dopoDi.parentNode.insertBefore(daInserire, dopoDi.nextSibling);
  }
})


/** Mobile Redirect Helper ************************************************
 *
 *  Redirects to the mobile-optimized gateway at it.m.wikipedia.org
 *  for viewers on iPhone, iPod Touch, Palm Pre, and Android devices.
 *
 *  You can turn off the redirect by setting the cookie "stopMobileRedirect=true"
 *
 *  This code cannot be imported, because the JS only loads after all other files
 *  and this was causing major issues for users with mobile devices. Must be loaded
 *  *before* the images and etc of the page on all mobile devices.
 *
 *  Maintainer: [[User:Brion VIBBER]], [[User:hcatlin]]
 */
if (/(Android|iPhone|iPod|webOS|NetFront|Opera Mini|SEMC-Browser|PlayStation Portable|Nintendo Wii)/.test(navigator.userAgent)) {
 
  var wgMainPageName = 'Main_Page';
 
  var stopMobileRedirectCookieExists = function() {
    return (document.cookie.indexOf("stopMobileRedirect=true") >= 0);
  }
 
  var mobileSiteLink = function() {
    if (wgCanonicalNamespace == 'Special' && wgCanonicalSpecialPageName == 'Search') {
        var pageLink = '?search=' + encodeURIComponent(document.getElementById('searchText').value);
    } else if (wgPageName == wgMainPageName) {
        var pageLink = '::Home'; // Special case
    } else {
        var pageLink = encodeURIComponent(wgPageName).replace('%2F','/').replace('%3A',':');
    }
    return 'http://' + wgContentLanguage + '.m.wikipedia.org/wiki/' + pageLink + "?wasRedirected=true"

  }
  if (!stopMobileRedirectCookieExists()) {
    document.location = mobileSiteLink();
  }
}

/* MediaWiki:Monobook.js */
/* <pre> */
/* tooltips and access keys */
ta = new Object();
ta['pt-userpage'] = new Array('.','La mia pagina utente'); 
ta['pt-anonuserpage'] = new Array('.','La pagina utente dell\'ip che stai usando'); 
ta['pt-mytalk'] = new Array('n','Le mie discussioni'); 
ta['pt-anontalk'] = new Array('n','Discussioni riguardo le modifiche fatte da questo ip'); 
ta['pt-preferences'] = new Array('','Le mie preferenze'); 
ta['pt-watchlist'] = new Array('l','La lista delle pagine che stai tenendo sotto sorveglianza.'); 
ta['pt-mycontris'] = new Array('y','La lista dei miei contributi'); 
ta['pt-login'] = new Array('o','Ti consigliamo di registrarti, ma non è obbligatorio.'); 
ta['pt-anonlogin'] = new Array('o','Ti consigliamo di registrarti, ma non è obbligatorio.'); 
ta['pt-logout'] = new Array('o','Log out (esci)'); 
ta['ca-talk'] = new Array('t','Vedi le discussioni relative alla voce'); 
ta['ca-edit'] = new Array('e','Puoi modificare questa pagina. Per favore usa il bottone di anteprima prima di salvare.'); 
ta['ca-addsection'] = new Array('+','Aggiungi un commento a questa discussione.'); 
ta['ca-viewsource'] = new Array('e','Questa pagina è protetta, ma puoi vedere il suo codice sorgente.'); 
ta['ca-history'] = new Array('h','Versioni precedenti di questa pagina.'); 
ta['ca-protect'] = new Array('=','Proteggi questa pagina'); 
ta['ca-delete'] = new Array('d','Cancella questa pagina'); 
ta['ca-undelete'] = new Array('d','Ripristina la pagina com\'era prima della cancellazione'); 
ta['ca-move'] = new Array('m','Sposta questa pagina ad un altro titolo'); 
ta['ca-nomove'] = new Array('','Non hai il permesso di spostare questa pagina'); 
ta['ca-watch'] = new Array('w','Aggiungi questa pagina all\'elenco delle pagine che tieni sotto sorveglianza'); 
ta['ca-unwatch'] = new Array('w','Togli questa pagina dall\'elenco delle pagine che tieni sotto sorveglianza'); 
ta['search'] = new Array('f','Cerca questa wiki'); 
ta['p-logo'] = new Array('','Pagina Principale'); 
ta['n-mainpage'] = new Array('z','Visita la Pagina principale'); 
ta['n-portal'] = new Array('','Descrizione del progetto, cosa puoi fare, e dove trovare le cose'); 
ta['n-sitesupport'] = new Array('','Aiutaci'); 
ta['n-recentchanges'] = new Array('r','La lista delle ultime modifiche a questa wiki.'); 
ta['n-randompage'] = new Array('x','Mostra una pagina a caso'); 
ta['n-help'] = new Array('a','Pagine di aiuto, manuale, domande frequenti'); 
ta['n-villagepump'] = new Array('b','Per discutere di Wikipedia'); 
ta['n-wikipediano'] = new Array('','Per vedere gli ultimi avvenimenti di Wikipedia');
ta['n-contact'] = new Array('','I contatti di Wikipedia e Wikimedia Italia'); 
ta['n-vetrina'] = new Array('v','Le migliori voci di Wikipedia'); 
ta['n-currentevents'] = new Array('','Eventi di attualità'); 
ta['t-whatlinkshere'] = new Array('j','Lista di tutte le pagine che portano a questa'); 
ta['t-recentchangeslinked'] = new Array('k','Lista delle ultime modifiche alle pagine linkate da questa.'); 
ta['feed-rss'] = new Array('','feed RSS per questa pagina'); 
ta['feed-atom'] = new Array('','feed Atom per questa pagina'); 
ta['t-contributions'] = new Array('','Lista dei contributi di questo utente'); 
ta['t-emailuser'] = new Array('','Manda un\'E.mail a questo utente'); 
ta['t-upload'] = new Array('u','Carica immagini o file multimediali su wikipedia'); 
ta['t-specialpages'] = new Array('q','Lista di tutte le pagine speciali'); 
ta['ca-nstab-main'] = new Array('c','Vedi la voce relativa'); 
ta['ca-nstab-user'] = new Array('c','Vedi la pagina utente'); 
ta['ca-nstab-media'] = new Array('c','Vedi la pagina del file multimediale'); 
ta['ca-nstab-special'] = new Array('','Questa è una pagina speciale, non può essere modificata.'); 
ta['ca-nstab-wp'] = new Array('','Vedi la pagina del progetto'); 
ta['ca-nstab-image'] = new Array('c','Vedi la pagina dell\'immagine'); 
ta['ca-nstab-mediawiki'] = new Array('c','Vedi il messaggio di sistema'); 
ta['ca-nstab-template'] = new Array('c','Vedi il template'); 
ta['ca-nstab-help'] = new Array('c','Vedi la pagina di aiuto'); 
ta['ca-nstab-category'] = new Array('c','Vedi la pagina della categoria');
ta['ca-nstab-portal'] = new Array('c','Vedi il portale');



/*** Collegamento diretto alla pagina di upload di Commons nel menu di sinistra, da WP:PT */
function linkUploadToCommons()
{
    if (document.getElementById("carrega-para-commons")) return;
    var li_carrega = document.getElementById("t-upload");
    if (!li_carrega) return;
    var afegit = li_carrega.nextSibling;
    var ul_eines = li_carrega.parentNode;
    var li = document.createElement("li");
    li.id = "carrega-para-commons";
    li.innerHTML = '<a href="http://commons.wikimedia.org/wiki/Commons:Upload/it">Carica su Commons</a>';
    if (afegit) ul_eines.insertBefore(li, afegit);
    else ul_eines.appendChild(li);
}

addLoadEvent(linkUploadToCommons);

/*** Fine del collegamento diretto alla pagina di upload di Commons */



function LinkAdQ() 
{
    if ( document.getElementById( "p-lang" ) ) {
        var InterwikiLinks = document.getElementById( "p-lang" ).getElementsByTagName( "li" );

        for ( var i = 0; i < InterwikiLinks.length; i++ ) {
            if ( document.getElementById( InterwikiLinks[i].className + "-fa" ) ) {
                InterwikiLinks[i].className += " FA"
                InterwikiLinks[i].title = "La voce è in vetrina in questa lingua.";
            }
        }
    }
}

addLoadEvent( LinkAdQ );

//============================================================
// Menu caratteri speciali
//============================================================

/**
 * Aggiunge il menu a tendina per selezionare un sottoinsieme di caratteri speciali
 * Attenzione:        l'ordine della lista deve corrispondere a quello di MediaWiki:Edittools !
 */
function aggiungiMenuSubsetCaratteri() {
  var specialchars = document.getElementById('specialchars');

  if (specialchars) {
    var menu = "<select style=\"display:inline\" onChange=\"scegliSubsetCaratteri(selectedIndex)\">";
    menu += "<option>Latino esteso</option>";
    menu += "<option>Wiki</option>";
    menu += "<option>Greco antico</option>";
    menu += "<option>Greco moderno</option>";
    menu += "<option>Cirillico</option>";
    menu += "<option>Arabo</option>";
    menu += "<option>Ebraico e yiddish</option>";
    menu += "<option>Armeno</option>";
    menu += "<option>Geroglifico</option>";
    menu += "<option>Vietnamita</option>";
    menu += "<option>IPA</option>";
    menu += "<option>Matematica</option>";
    menu += "</select>";
    specialchars.innerHTML = menu + specialchars.innerHTML;

    /* default subset - try to use a cookie some day */
    scegliSubsetCaratteri(0);
  }
}

/* select subsection of special characters */
function scegliSubsetCaratteri(s) {
  var l = document.getElementById('specialchars').getElementsByTagName('p');
  for (var i = 0; i < l.length ; i++) {
    l[i].style.display = i == s ? 'inline' : 'none';
    l[i].style.visibility = i == s ? 'visible' : 'hidden';
  }
}

addLoadEvent(aggiungiMenuSubsetCaratteri);

// END Menu caratteri speciali

var mpTitle = "Pagina principale";
var isMainPage = (wgPageName == "Pagina_principale");
var isDiff = (document.location.search && (document.location.search.indexOf("diff=") != -1 || document.location.search.indexOf("oldid=") != -1));

if (isMainPage && !isDiff) 
{
document.write('<style type="text/css">/*<![CDATA[*/ #lastmod, #siteSub, #contentSub, h1.firstHeading { display: none !important; } /*]]>*/</style>');

var mpSmallEnabled;
var mpMinWidth = 700;

function mainPageTransform()
{
       if ((isMainPage || /[\/=:]Pagina_principale/.test(document.location)) && document.getElementById('ca-nstab-main'))     
       document.getElementById('ca-nstab-main').firstChild.innerHTML = 'Pagina principale';
	var mpContentEl = document.getElementById("bodyContent");
	var mpBrowseEl = document.getElementById("EnWpMpBrowse");
	var mpContainEl = document.getElementById("EnWpMpBrowseContainer");
	var mpMarginEl = document.getElementById("EnWpMpMargin");
	var mpEl = document.getElementById("EnWpMainPage");

	if (!mpContentEl || !mpBrowseEl || !mpContainEl || !mpMarginEl || !mpEl)
		return;

	if (!mpSmallEnabled && mpContentEl.offsetWidth < mpMinWidth)
	{
		mpContainEl.insertBefore(mpBrowseEl, mpContainEl.firstChild);
		mpBrowseEl.className = "EnWpMpBrowseBottom";
		mpMarginEl.style.marginRight = 0;
		mpSmallEnabled = true;
	}
	else if (mpSmallEnabled && mpContentEl.offsetWidth > mpMinWidth)
	{
		mpEl.insertBefore(mpBrowseEl, mpEl.firstChild);
		mpBrowseEl.className = "EnWpMpBrowseRight";
		mpMarginEl.style.marginRight = "13.8em";
		mpSmallEnabled = false;
	}
}

var onloadFuncts = [ mainPageTransform ];

if (window.addEventListener) 
  window.addEventListener("resize", mainPageTransform, false);
else if (window.attachEvent) 
  window.attachEvent("onresize", mainPageTransform);

}

/* aggiunge un pulsante per i redirect nella toolbar della finestra di edit */
if (mwCustomEditButtons) {
  mwCustomEditButtons[mwCustomEditButtons.length] = {
    "imageFile": "http://upload.wikimedia.org/wikipedia/en/c/c8/Button_redirect.png",
    "speedTip": "Redirect",
    "tagOpen": "#REDIRECT [[",
    "tagClose": "]]",
    "sampleText": "Inserisci nome della voce"};
};

 /** "Technical restrictions" title fix *****************************************
  *
  *  Description:
  *  Maintainers: [[User:Interiot]], [[User:Mets501]]
  *  copiato da [[:en:MediaWiki:Common.js]]
  */
 
 // For pages that have something like Template:Lowercase, replace the title, but only if it is cut-and-pasteable as a valid wikilink.
 //	(for instance [[iPod]]'s title is updated.  <nowiki>But [[C#]] is not an equivalent wikilink, so [[C Sharp]] doesn't have its main title changed)</nowiki>
 //
 // The function looks for a banner like this: <nowiki>
 // <div id="RealTitleBanner">    <!-- div that gets hidden -->
 //   <span id="RealTitle">title</span>
 // </div>
 // </nowiki>An element with id=DisableRealTitle disables the function.
 var disableRealTitle = 0;		// users can disable this by making this true from their monobook.js
 function correctTitle() {
 	try {
 		var realTitleBanner = document.getElementById("RealTitleBanner");
 		if (realTitleBanner && !document.getElementById("DisableRealTitle") && !disableRealTitle) {
 			var realTitle = document.getElementById("RealTitle");
 			if (realTitle) {
 				var realTitleHTML = realTitle.innerHTML;
 				realTitleText = pickUpText(realTitle);
 
 				var isPasteable = 0;
 				//var containsHTML = /</.test(realTitleHTML);	// contains ANY HTML
 				var containsTooMuchHTML = /</.test( realTitleHTML.replace(/<\/?(sub|sup|small|big|a)>/gi, "") ); // contains HTML that will be ignored when cut-n-pasted as a wikilink
 					// in più, aggiunto ''a'' per mantenere funzionanti alcuni usi del vecchio Titolo errato - non è detto però che sia sempre voluto
 				// questo codice era in en.wiki, e se ho ben capito serviva per controllare che il nome nuovo fosse abbastanza simile all'originale, ma non è quello che vogliamo su it.wiki
 				// // calculate whether the title is pasteable
 				// var verifyTitle = realTitleText.replace(/^ +/, "");		// trim left spaces
 				// verifyTitle = verifyTitle.charAt(0).toUpperCase() + verifyTitle.substring(1, verifyTitle.length);	// uppercase first character
 
 				// questo codice era in en.wiki, e aggiunge il prefisso del namespace se non presente, ma su it.wiki ci si aspetta che ciò non avvenga
 				// // if the namespace prefix is there, remove it on our verification copy.  If it isn't there, add it to the original realValue copy.
 				// if (wgNamespaceNumber != 0) {
 				// 	if (wgCanonicalNamespace == verifyTitle.substr(0, wgCanonicalNamespace.length).replace(/ /g, "_") && verifyTitle.charAt(wgCanonicalNamespace.length) == ":") {
 				// 		verifyTitle = verifyTitle.substr(wgCanonicalNamespace.length + 1);
 				// 	} else {
 				// 		realTitleText = wgCanonicalNamespace.replace(/_/g, " ") + ":" + realTitleText;
 				// 		realTitleHTML = wgCanonicalNamespace.replace(/_/g, " ") + ":" + realTitleHTML;
 				// 	}
 				// }
 
 				// come sopra, faceva controlli sul titolo
 				// // verify whether wgTitle matches
 				// verifyTitle = verifyTitle.replace(/^ +/, "").replace(/ +$/, "");		// trim left and right spaces
 				// verifyTitle = verifyTitle.replace(/_/g, " ");		// underscores to spaces
 				// verifyTitle = verifyTitle.charAt(0).toUpperCase() + verifyTitle.substring(1, verifyTitle.length);	// uppercase first character
 				// isPasteable = (verifyTitle == wgTitle);
 
 				var h1 = document.getElementsByTagName("h1")[0];
 				if (h1) {     // tolto il controllo isPasteable
 					h1.innerHTML = containsTooMuchHTML ? realTitleText : realTitleHTML;
 					// if (!containsTooMuchHTML) // ancora, adattamento per it.wiki
 						realTitleBanner.style.display = "none";
 				}
 				document.title = realTitleText + " - Wikipedia";
 			}
 		}
 	} catch (e) {
 		/* Something went wrong. */
 	}
 }
 addOnloadHook(correctTitle);
 
 // similar to innerHTML, but only returns the text portions of the insides, excludes HTML
 function pickUpText(aParentElement) {
   var str = "";
 
   function pickUpTextInternal(aElement) {
     var child = aElement.firstChild;
     while (child) {
       if (child.nodeType == 1)		// ELEMENT_NODE 
         pickUpTextInternal(child);
       else if (child.nodeType == 3)	// TEXT_NODE
         str += child.nodeValue;
 
       child = child.nextSibling;
     }
   }
 
   pickUpTextInternal(aParentElement);
 
   return str;
 }

/** Fix al layout della Pagina principale *********************************************************
  *
  *  Descrizione:        Alcuni al layout, fra cui un link alla lista completa di tutte
  *                      le wikipedia in tutte le lingue disponibili.
  *  Da un codice di:    [[en:User:AzaToth]], [[en:User:R. Koot]]
  *  Sistemato da:       [[n:Utente:Tooby]] <--- liberi di giustiziarlo
  */
 function mainPageRenameNamespaceTab() {
     try {
         var Node = document.getElementById( 'ca-nstab-main' ).firstChild;
         if ( Node.textContent ) {      // Per DOM Level 3
             Node.textContent = 'Pagina principale';
         } else if ( Node.innerText ) { // IE doesn't handle .textContent
             Node.innerText = 'Pagina principale';
         } else {                       // Fallback
             Node.replaceChild( Node.firstChild, document.createTextNode( 'Pagina principale' ) ); 
         }
     } catch(e) {
         // bailing out!
     }
 }

  function mainPageAppendCompleteListLink() {
     try {
         var node = document.getElementById( "p-lang" )
                            .getElementsByTagName('div')[0]
                            .getElementsByTagName('ul')[0];
 
         var aNode = document.createElement( 'a' );
         var liNode = document.createElement( 'li' );
 
         aNode.appendChild( document.createTextNode( 'Lista completa' ) );
         aNode.setAttribute( 'href' , 'http://meta.wikimedia.org/wiki/Lista_di_Wikipedie' );
         liNode.appendChild( aNode );
         liNode.className = 'interwiki-completelist';
         node.appendChild( liNode );
      } catch(e) {
        // lets just ignore what's happened
        return;
     }
 }
 
 if ( wgTitle == 'Pagina principale' && ( wgNamespaceNumber == 0 || wgNamespaceNumber == 1 ) ) {
        addOnloadHook( mainPageRenameNamespaceTab );
 }
 
 if ( wgTitle == 'Pagina principale' && wgNamespaceNumber == 0 ) {
        addOnloadHook( mainPageAppendCompleteListLink );
 }

// Lista di voci trasferite ad altri progetti - caricato solo se si visualizza una pagina nel namespace principale
if (wgNamespaceNumber == 0 && wgTitle != 'Pagina principale' && wgArticleId == 0)
    document.write('<script type="text/javascript" src="' 
             + 'http://it.wikipedia.org/w/index.php?title=Wikipedia:Proposte di trasferimento/transferlist.js' 
             + '&action=raw&ctype=text/javascript&dontcountme=s"></script>');

function show_transfertext()
{
    if (wgNamespaceNumber != 0 || (wgNamespaceNumber == 0 && wgTitle == 'Pagina principale') || wgArticleId != 0)
        return;

 setup_transferlist();
 var i;
 for(i = 0; i <= transferlist.length; i+=4)
 {
  if (document.title == 'Modifica di ' + transferlist[i] + ' - Wikipedia' || document.title == transferlist[i] + ' - Wikipedia')
  {
   var itm = document.getElementById('BoxTrasferimento');
   if (itm == null) return;
   txt = itm.innerHTML;
   if (transferlist[i+1] == 'b' || transferlist[i+1] == 'wb')
    transferlist[i+1] = 'http://it.wikibooks.org/wiki/';
   if (transferlist[i+1] == 's' || transferlist[i+1] == 'ws')
    transferlist[i+1] = 'http://it.wikisource.org/wiki/';
   if (transferlist[i+1] == 'q' || transferlist[i+1] == 'wq')
    transferlist[i+1] = 'http://it.wikiquote.org/wiki/';
   if (transferlist[i+1] == 'n' || transferlist[i+1] == 'wn')
    transferlist[i+1] = 'http://it.wikinews.org/wiki/';
   if (transferlist[i+1] == 'wikt')
    transferlist[i+1] = 'http://it.wiktionary.org/wiki/';
   if (transferlist[i+1] == 'v' || transferlist[i+1] == 'wv')
    transferlist[i+1] = 'http://it.wikiversity.org/wiki/';
   if (transferlist[i+1] == 'wikispecies' || transferlist[i+1] == 'wsp')
    transferlist[i+1] = 'http://species.wikimedia.org/wiki/';
   if (transferlist[i+1] == 'm' || transferlist[i+1] == 'meta')
    transferlist[i+1] = 'http://meta.wikimedia.org/wiki/';
   if (transferlist[i+1] == 'commons' || transferlist[i+1] == 'com')
    transferlist[i+1] = 'http://commons.wikimedia.org/wiki/';
   if (transferlist[i+3] == "")
    transferlist[i+3] = transferlist[i];
   txt = '<div class="AvvisoTrasferito">Una vecchia versione di questa pagina è stata <b>trasferita</b> ' +
         '<a href=' + transferlist[i+1] + transferlist[i+3].replace(/ /g, '_') + '>all\'interno di un altro progetto Wikimedia</a>' +
         (transferlist[i+2].length > 1 ? ' perché: <i>' + transferlist[i+2] + '</i>' : "") + //solo se la motivazione è fornita
         '. Se ritieni la voce enciclopedica puoi riscriverla, controllando che siano stati eliminati i motivi che ne hanno comportato ' +
         'la cancellazione.</div>' + txt;
   itm.innerHTML = txt;
   break;
  }
 }
}

addLoadEvent(show_transfertext);

//****************** WIKI MINI ATLAS ******************
document.write('<script type="text/javascript" src="' 
     + 'http://meta.wikimedia.org/w/index.php?title=MediaWiki:Wikiminiatlas.js' 
     + '&action=raw&ctype=text/javascript&dontcountme=s"></script>');

/*
Correzione della posizione del link [modifica] delle sezioni.
 
Copyright 2006, Marc Mongenet
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
See http://www.gnu.org/licenses/gpl.html
 
The function looks for <span class="editsection">, and move them
at the end of their parent and display them inline in small font.
var oldEditsectionLinks=true disables the function.
*/
 
setModifySectionStyle = function()
{
try {
        if (!(typeof oldEditsectionLinks == 'undefined' || oldEditsectionLinks == false)) return;
        var spans = document.getElementsByTagName("span");
        for (var s = 0; s < spans.length; ++s) {
                var span = spans[s];
                if (span.className == "editsection") {
                        span.style.fontSize = "x-small";
                        span.style.fontWeight = "normal";
                        span.style.cssFloat = span.style.styleFloat = "none";
                        span.parentNode.appendChild(document.createTextNode(" "));
                        span.parentNode.appendChild(span);
                }
        }
} catch (e) { /* something went wrong */ }
}
addOnloadHook(setModifySectionStyle);
 

/*** CODICE PER IL TEMPLATE:GALLERIA ***/
function toggleImage(group, remindex, shwindex) {
  document.getElementById("ImageGroupsGr"+group+"Im"+remindex).style.display="none";
  document.getElementById("ImageGroupsGr"+group+"Im"+shwindex).style.display="inline";
}
function ImageGroup(){
	if (document.URL.match(/printable/g)) return;
	var bc=document.getElementById("bodyContent");
	var divs=bc.getElementsByTagName("div");
	var i = 0, j = 0;
	var units, search;
	var currentimage;
	var UnitNode;
	for (i = 0; i < divs.length ; i++) {
		if (divs[i].className != "ImageGroup") continue;
		UnitNode=undefined;
		search=divs[i].getElementsByTagName("div");
		for (j = 0; j < search.length ; j++) {
			if (search[j].className != "ImageGroupUnits") continue;
			UnitNode=search[j];
			break;
		}
		if (UnitNode==undefined) continue;
		units=Array();
		for (j = 0 ; j < UnitNode.childNodes.length ; j++ ) {
			var temp = UnitNode.childNodes[j];
			if (temp.className=="center") units.push(temp);
		}
		for (j = 0 ; j < units.length ; j++) {
			currentimage=units[j];
			currentimage.id="ImageGroupsGr"+i+"Im"+j;
			var imghead = document.createElement("div");
			var leftlink;
			var rightlink;
			if (j != 0) {
				leftlink = document.createElement("a");
				leftlink.href = "javascript:toggleImage("+i+","+j+","+(j-1)+");";
				leftlink.innerHTML="◀";
			} else {
				leftlink = document.createElement("span");
				leftlink.innerHTML=" ";
			}
			if (j != units.length - 1) {
				rightlink = document.createElement("a");
				rightlink.href = "javascript:toggleImage("+i+","+j+","+(j+1)+");";
				rightlink.innerHTML="▶";
			} else {
				rightlink = document.createElement("span");
				rightlink.innerHTML=" ";
			}
			var comment = document.createElement("tt");
			comment.innerHTML = "("+ (j+1) + "/" + units.length + ")";
			with(imghead) {
				style.fontSize="110%";
				style.fontweight="bold";
				appendChild(leftlink);
				appendChild(comment);
				appendChild(rightlink);
			}
			currentimage.insertBefore(imghead,currentimage.childNodes[0]);
			if (j != 0) currentimage.style.display="none";
		}
	}
}
addOnloadHook(ImageGroup);

// Toglie "Soggetto/Intestazione" nelle creazioni delle discussioni al Bar
if (wgNamespaceNumber == 4 &&
    wgPageName.indexOf("Wikipedia:Bar/") == 0 &&
    wgAction == "edit" &&
    document.URL.indexOf("&section=new&preload=") != -1)
addLoadEvent ( function ()
{
  document.getElementById('wpSummaryLabel').style.display="none";
  document.getElementById('wpSummary').style.display="none";
});


// INIZIO Collegamenti Interprogetto (codice adattato da de:wikt:Mediawiki:monobook.js)

document.write('<style type="text/css">#interProject {display: none; speak: none;} #p-tb .pBody {padding-right: 0;}<\/style>');
 function iProject() {
  if (document.getElementById("interProject")) {
   var iProject = document.getElementById("interProject").innerHTML;
   var interProject = document.createElement("div");
   interProject.style.marginTop = "0.7em";
   interProject.innerHTML = '<h5>altri progetti<\/h5><div class="pBody">'+iProject+'<\/div>';
   document.getElementById("p-tb").appendChild(interProject);
  }
 }
 hookEvent("load", iProject);

// FINE Collegamenti Interprogetto
/* </pre> */