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
}

async function findByWoord(woord) {
    const response = await fetch(`klanten?naamBevat=${woord}`);
    if (response.ok) {
        const klanten = await response.json();
        console.log(klanten)
        toon("klantenTable");
        const klantenBody = byId("klantenBody");
        console.log(klantenBody)
        verwijderChildElementenVan(klantenBody);
        for (const klant of klanten) {
            const tr = klantenBody.insertRow();
            const klantNaamVoornaamCell = tr.insertCell();
            klantNaamVoornaamCell.innerText = klant.familienaam + " " + klant.voornaam;
            klantNaamVoornaamCell.addEventListener('click', function () {
                window.location.href = 'bevestig.html?id=' + klant.id;
                sessionStorage.setItem("klantDatas", JSON.stringify(klant))
            });
            tr.insertCell().innerText = klant.straatNummer;
            tr.insertCell().innerText = klant.postcode;
            tr.insertCell().innerText = klant.gemeente;
        }
    } else {
        toon("storing");
    }
}