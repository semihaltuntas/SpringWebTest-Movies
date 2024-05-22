"use strict";
import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzasEnFouten();
    const woordInput = byId("woord");
    if (woordInput.checkValidity()) {
        findByWoord(woordInput.value);
    } else {
        toon("woordFout");
        woordInput.focus();
    }
};

function verbergPizzasEnFouten() {
    verberg("klantenTable");
    verberg("woordFout");
    verberg("storing");
    verberg("geenResultaten");
}

async function findByWoord(woord) {
    const response = await fetch(`klanten?naamBevat=${woord}`);
    if (response.ok) {
        const klanten = await response.json();
       // console.log(klanten)
        const klantenBody = byId("klantenBody");
        verwijderChildElementenVan(klantenBody);

        if (klanten.length === 0) {
            toon("geenResultaten");
            verberg("KlantenTable");
        } else {
            for (const klant of klanten) {
                toon("klantenTable");
                const tr = klantenBody.insertRow();
                const klantNaamVoornaamCell = tr.insertCell();
                klantNaamVoornaamCell.innerText = klant.familienaam + " " + klant.voornaam;
                klantNaamVoornaamCell.style.textDecoration = "underline";
                klantNaamVoornaamCell.onclick = function () {
                    window.location.href = 'bevestig.html?id=' + klant.id;
                    sessionStorage.setItem("klantStorage", JSON.stringify(klant));
                };

                tr.insertCell().innerText = klant.straatNummer;
                tr.insertCell().innerText = klant.postcode;
                tr.insertCell().innerText = klant.gemeente;
            }
        }
    } else {
        toon("storing");
    }
}