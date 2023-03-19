<h1>Mise en place</h1>

<h2>Pré-requis</h2>

<ul>
    <li>JDK 8/11;</li>
    <li>IntelliJ (SonarLint).</li>
</ul>

<h2>Lancement</h2>

<h3>Démarrer le serveur H2</h3>

Exécuter, dans le dossier `src\main\resources` où l'archive `h2-1.4.200.jar` est présente, la commande :

``java -cp h2-1.4.200.jar org.h2.tools.Server -ifNotExists``

Si tout va bien, cela une fenêtre sur la console H2 doit s'ouvrir. Si ce n'est pas le cas, vous pouvez accéder à la console en allant sur [http://localhost:8082](http://localhost:8082/) .

<u>Remarque :</u> Si vous rencontrez un problème pour vous connecter à la base H2, essayez de télécharger à nouveau le jar en allant sur [https://mvnrepository.com/artifact/com.h2database/h2/2.1.214](https://mvnrepository.com/artifact/com.h2database/h2/2.1.214) .

<h3>Configurer le serveur et la console H2 dans IntelliJ</h3>

A partir de la fenêtre `Database` de votre interface `IntelliJ`, sélectionnez `New [+] > Data Source > H2`. Puis, dans `Data Sources > General` modifier le champ `URL` par `jdbc:h2:tcp://localhost/~/h2DB`. Pour finir, cliquez sur `Apply`. Désormais, vous pouvez visualiser le contenu de la base et l'interroger. 

<h3>Démarrer l'application</h3>

Configurer le fichier démarrage `src/main/java/fr/uge/jee/ugeoverflow/Application.java` dans l'interface `Edit configuration` de `IntelliJ`.

Lorsque l'application est démarré, vous pouvez accéder à l'interface de `Swagger UI` en allant sur [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui/index.html). 
Cette interface est utile pour tester vos **API REST** et les documenter.

<h3>Documentation</h3>
<ul>
    <li>Spring in Action, 5th Edition ;</li>
    <li>Spring Boot In Action ;</li>
    <li>Java Persistence with Hibernate, 2nd Edition ;</li>
    <li><a href="https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html">Application properties file documentation</a> ;
    <li><a href="https://projectlombok.org/features/">Lombok Project documentation</a> ;</li>
    <li><a href="https://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/reference/htmlsingle/">Spring Security documentation</a>.</li>
</ul>

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
