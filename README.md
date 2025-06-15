# Rent-A-Car SpringBoot App

#### Nume Student: Sandu Eduard Alexandru 
#### Grupa: BDTS - 405

## Descrierea aplicatiei

In aceasta aplicatie Spring Boot vom realiza o platforma interna pentru un business de inchiriat masini
Astfel, utilizatorii autorizati din cadrul acestui business pot folosi platforma din mai multe locatii pentru a gestiona fluxul unei rezervari atunci cand un client doreste sa inchirieze o masina.
### MVP Phase features

## Baza de date a aplicatiei

![image](https://github.com/user-attachments/assets/2ace6ae7-1bcb-4ae3-b132-97f31af5b79a)

Se pot observa urmatoarele:

- Tabelele de entitati **users**, **bookings**, **cars**, **payments**, **manufacturers**
- Tabela de **audit**
- Tabela **admins** care constituie operatorii autorizati ce au drepturi asupra platformei
- Relatiile sunt one to many in mare parte, mai putin intre **bookings** si **cars**, unde acel many-to-many a fost rezolvat printr-o tabela de legatura numita **booking_cars**

## Endpointuri si functionalitatile aplicatiei 

### Controllerul clientilor (care vor face inchirierile)

Acest controller detine mai multe metode prin care putem inregistra clientii noi sau vizualiza date despre acestia (date personale, email, data inregistrarii in baza de date etc.). Acestia trebuie sa fie inregistrati in baza de date de catre utilizatorii autorizati pentru a putea inchiria un autovehicul.

![image](https://github.com/user-attachments/assets/9d9e982b-e02a-418e-a4fc-248dc10c939e)

## Controllerul rezervarilor (prin care se vor manageria inchirierile de masini)

Acest controller se ocupa managerierea inchirierilor noi sau existente. La nivel de service, se vor efectua validari in functie de disponibilitatea masinilor sau de limitarile clientilor de a inchiria o masina, precum si de calcularea pretului final de inchiriere.

![image](https://github.com/user-attachments/assets/c438f717-b44c-4f8a-aa40-56f132da22c9)

## Controllerul masinilor (prin care putem manageria masinile, le putem filtra si le putem verifica disponibilitatea)

Acest controller dispune de operatiile CRUD de baza pentru masini, dar ne ajuta si la filtrarea acestora in functie de caracteristici. Tot aici putem verifica daca una dintre masini este disponibila pentru inchiriere intr-un interval de date (spre exemplu, pentru 3 zile, intre 19 ianuarie si 22 ianuarie)

![image](https://github.com/user-attachments/assets/f5d4c7b3-2003-4082-bdb7-5351b1b0d2fc)

## Controllerul platilor (prin care putem achita inchirierile, in una sau mai multe transe)

Controllerul de plati se ocupa cu stabilirea daca o rezervare este in asteptare sau confirmata - o rezervare confirmata este o rezervare a caror plati insumate rezulta totalul de plata al inchirierii. Asadar inserarea sau stergerea unei plati va afecta statutul unei rezervari.

![image](https://github.com/user-attachments/assets/dc509b49-6366-4031-94b6-dc22ee4b67b5)

## Controllerul producatorilor de masini (prin care putem manageria producatorii masinilor din baza de date)

Acest controller este necesar atunci cand dorim sa extindem sau sa micsoram gama marcilor de masini, sau dorim sa aflam toate masinile puse la dispozitie de un producator (Spre exemplu, toate masinile pe care le avem spre inchiriere de la Mercedes)

![image](https://github.com/user-attachments/assets/302fb590-628b-47f1-8b90-6686a816e515)

## Controllerul de audit (prin care sunt inregistrate actiunile utilizatorilor firmei pe platforma)

Acest controller este folosit in cazul in care dorim sa vizualizam tabela de audit. In speta, service-ul de audit este folosit sub forma unui aspect in aplicatie si inregistreaza automat rularea functiilor la nivel de service-uri, astfel inregistrand actiunile utilizatorilor.

![image](https://github.com/user-attachments/assets/f7b6f92b-303f-45d9-b6c0-4d07e12f7938)

## Testing folosind JUnit si Mockito

Testarea s-a realizat folosind JUnit si Mockito, fiind direct suportate de spring boot prin dependencies si nu necesita setup in plus. Pentru fiecare controller, am mockuit service-urile folosite si am realizat un request folosind **mockMvc**:

![image](https://github.com/user-attachments/assets/9d538763-07de-48b2-887c-116b2c88131e)

Pentru a testa service-urile, am mockuit repository-urile folosite si am creat sample objects care sa fie returnate de acestea, pentru a simula interactiunea cu ele si a verifica ca acestea returneaza ce ne-am dorit:

![image](https://github.com/user-attachments/assets/77c88369-1b67-4126-9161-dbb92877da03)

Pentru a rula toate testele, folosim urmatoarea comanda in terminal:

```
mvn -Dtest=* test
```

![image](https://github.com/user-attachments/assets/fb78eccd-b744-401b-90e4-ebbe955bb485)

