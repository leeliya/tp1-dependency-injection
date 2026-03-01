# TP1 — Injection des dépendances

## 1) Objectif du TP

Ce TP a pour but de mettre en œuvre le principe d’**inversion de contrôle (IoC)** et l’**injection des dépendances (DI)** en Java, à travers plusieurs approches :

- Couplage fort (instanciation directe)
- Injection dynamique par réflexion (fichier de configuration)
- Injection via Spring XML
- Injection via annotations Spring

---

## 2) Structure du projet

Le projet est organisé en couches :

- `dao` : accès aux données
	- `IDao` : contrat DAO
	- `DaoImpl` : implémentation version base de données
- `ext` : implémentations alternatives
	- `DaoImplV2` : version capteurs
- `metier` : logique métier
	- `IMetier` : contrat métier
	- `MetierImpl` : traitement métier dépendant de `IDao`
- `pres` : couches de présentation / démarrage
	- `Pres1` : couplage fort
	- `Pres2` : réflexion + fichier `config.txt`
	- `PresSpringXML` : DI avec Spring XML (`config.xml`)
	- `PresSpringAnnotation` : DI avec annotations (`@Component`, `@Autowired`)

---

## 3) Conception (interfaces et implémentations)

### 3.1 Interface DAO

`IDao` définit la méthode :

```java
double getData();
```

### 3.2 Implémentations DAO

- `DaoImpl` retourne une valeur simulée (base de données)
- `DaoImplV2` retourne une valeur simulée (capteurs)

### 3.3 Interface métier

`IMetier` définit la méthode :

```java
double calcul();
```

### 3.4 Implémentation métier

`MetierImpl` dépend de `IDao` et calcule :

```java
res = t * 12 * Math.PI / 2 * Math.cos(t)
```

où `t` est récupéré depuis `dao.getData()`.

`MetierImpl` supporte :

- injection par constructeur
- injection par setter (`setDao`)
- injection Spring par annotation (`@Autowired` + `@Qualifier("d2")`)

---

## 4) Les approches d’injection réalisées

### 4.1 Version 1 — Couplage fort (`Pres1`)

Dans `Pres1`, les classes concrètes sont instanciées directement :

```java
DaoImplV2 d = new DaoImplV2();
MetierImpl metier = new MetierImpl(d);
```

✅ Simple à comprendre  
❌ Fort couplage (difficile à faire évoluer)

---

### 4.2 Version 2 — Réflexion + configuration externe (`Pres2`)

`Pres2` lit les noms de classes depuis `config.txt`, puis crée les objets dynamiquement via réflexion.

Contenu actuel de `config.txt` :

```txt
net.layla.dao.DaoImpl
net.layla.metier.MetierImpl
```

Injection effectuée via le setter :

```java
Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
setDao.invoke(metier, d);
```

✅ Découplage amélioré  
✅ Changement d’implémentation sans recompiler le code métier  
❌ Plus verbeux/fragile (réflexion)

---

### 4.3 Version 3 — Injection avec Spring XML (`PresSpringXML`)

Les beans sont déclarés dans `src/main/resources/config.xml` :

- bean `d` : `net.layla.dao.DaoImpl`
- bean `metier` : `net.layla.metier.MetierImpl`
- propriété `dao` injectée avec `ref="d"`

Puis chargés avec :

```java
ApplicationContext springContext = new ClassPathXmlApplicationContext("config.xml");
IMetier metier = (IMetier) springContext.getBean("metier");
```

✅ Très bon découplage  
✅ Configuration centralisée  
❌ XML supplémentaire à maintenir

---

### 4.4 Version 4 — Injection avec annotations Spring (`PresSpringAnnotation`)

Les composants sont détectés automatiquement via :

```java
ApplicationContext context = new AnnotationConfigApplicationContext("net.layla");
```

Annotations utilisées :

- `@Component("metier")` sur `MetierImpl`
- `@Component("d")` sur `DaoImpl`
- `@Component("d2")` sur `DaoImplV2`
- `@Autowired` + `@Qualifier("d2")` pour choisir l’implémentation injectée

✅ Configuration légère et moderne  
✅ Très flexible  
✅ Forte maintenabilité

---

## 5) Dépendances Maven

Le projet utilise :

- `spring-core`
- `spring-context`
- `spring-beans`

Version actuelle : `6.2.7`

Compilateur Java configuré sur : `22`

---

## 6) Résultats observés

Selon l’implémentation injectée (`DaoImpl` ou `DaoImplV2`), la valeur de `t` change, donc le résultat final `RES` change également.

Exemples d’affichage possibles :

- `Version de base de données`
- `Version capteurs ...`
- `RES=...`

---

## 7) Comparaison synthétique

| Approche | Couplage | Flexibilité | Complexité |
|---|---|---|---|
| Instanciation directe (`Pres1`) | Fort | Faible | Faible |
| Réflexion + fichier (`Pres2`) | Moyen/Faible | Élevée | Moyenne |
| Spring XML | Faible | Élevée | Moyenne |
| Spring Annotations | Faible | Très élevée | Faible/Moyenne |

---

## 8) Conclusion

Ce TP montre l’évolution d’une architecture **fortement couplée** vers une architecture **modulaire, testable et extensible** grâce à l’injection des dépendances.

L’approche la plus adaptée en pratique est l’utilisation de **Spring avec annotations**, car elle combine :

- un bon niveau de découplage,
- une configuration réduite,
- une grande facilité d’évolution.

---

## 9) Auteurs

- Étudiante : Layla EL HAJJAJI
- Filière : BDCC2