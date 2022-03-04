Metoden sorteras enbart på x-axeln innan, istället för som boken nämner, att sortera den på X och Y - och sedan dela upp bägge på hälften på X för varje rekursion. 

Anledningen till att jag inte gör detta, är att jag märkte att algoritmens effektivitet blev sämre gentemot min nuvarande implementation när jag gjorde det, då den behövde iterera igenom Y-listan för varje steg för att skapa två nya Y-listor för varje rekursiva halva. 

Jag kan tänka mig att den andra lösningen med att sortera bägge fortfarande vore bättre om datat är väldigt klustrat runt mittenpunkterna - men jag anser det inte vara värt att anpassa algoritmen för det i det här specifika fallet, ifall det försämrar prestandan i de fall där datan är mer jämnt fördelat. 

Min åsikt är att man bör välja algoritm baserat på datan den ska behandla - och i det här fallet är datat jämnt fördelat genom att siffrorna är slumpmässigt genererade. 

