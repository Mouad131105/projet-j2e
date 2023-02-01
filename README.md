<h1>Structure du processus d'intégration</h1>

<h2>Convention de nommage pour les commits</h2>
```
<type>(scope optionnel): <sujet>

[description optionnelle]
```
<h3>Type</h3>
Le type du commit décrit l’origine du changement :
<ul>
    <li><code>feat</code> : Nouvelle fonctionnalité ;</li>
    <li><code>fix</code> : Correctif ;</li>
    <li><code>build</code> : Changement relatif aux dépendances ;</li>
    <li><code>docs</code> : Modification/ajout de documentation ;</li>
    <li><code>perf</code> : Amélioration des performances ;</li>
    <li><code>refactor</code> : Modification du code sans impact sur le fonctionnement de l'application ;</li>
    <li><code>style</code> : Modification lié au style du code ;</li>
    <li><code>test</code> : Modification/ajout de tests.</li>
</ul>

<h3>Scope</h3>
Le scope définit la partie de l'application concernée par le commit (e.g., ``config``, ``init``, etc.).

Si le commit affecte la globalité du code ou est difficile à définir, il peut être omis. 

<h3>Sujet</h3>

Décrire en quelques mots le/les changements opérés, à l'impératif, sans majuscules au début, et sans "." à la fin.
