"use strict";
import {byId, toon, verberg, setText, verwijderChildElementenVan} from "./util.js";

const mandjeDatas = JSON.parse(sessionStorage.getItem("mandjeStorage"));
const klantDatas = JSON.parse(sessionStorage.getItem("klantStorage"));

const bevestigBtn = byId("bevestigBtn");
const reservatieStatusUl = byId("reservatieStatus");
const bevestigH2element = byId("bevestigSubtitle")
document.addEventListener('DOMContentLoaded', () => {
    if (!klantDatas || !mandjeDatas) {
        toon('storing');
        bevestigBtn.disabled = true;
        return;
    }
    bevestigH2element.textContent = `${mandjeDatas.length} film(s) voor ${klantDatas.familienaam +" "+ klantDatas.voornaam}`

    bevestigBtn.addEventListener('click', async () => {
        const klantId = klantDatas.id;
        const mandjeTag = byId("mandje")

        bevestigBtn.disabled = true;
        mandjeTag.hidden=true;
        verwijderChildElementenVan(reservatieStatusUl);

        for (const film of mandjeDatas) {
            console.log(film)
            const response = await fetch(`films/${film.id}/reservaties`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({klantId: klantId})
            });

            const listItem = document.createElement('li');
            if (response.ok) {
                listItem.textContent = `${film.titel}: OK`;
                reservatieStatusUl.appendChild(listItem);
               // console.log(listItem)
            } else {
                switch (response.status) {
                    case 404:
                        toon("nietGevonden"); //Ik denk niet dat er een kans is dat we de film hier niet kunnen vinden.:))
                        break;
                    case 409:
                        const responseBody = await response.json();
                        const conflictMessage = `${film.titel}: ${responseBody.message}`
                        setText("conflict", conflictMessage);
                        toon("conflict");
                        break;
                    default:
                        toon("storing");
                }
            }
        }
        sessionStorage.removeItem("mandjeStorage");
    });
});
