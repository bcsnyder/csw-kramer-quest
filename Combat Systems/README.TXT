------------------------------------------------------------------------
COMBAT SYSTEMS BY: DYLAN
------------------------------------------------------------------------

Player
-health
-atk
-def
ouchie() - subtract an amount of health and return 1 if alive and 0 if dead.
getName() - returns chosen player name.

Monster <<abstract>>
-health
-atk
-def
ouchie() - subtract an amount of health and return 1 if alive and 0 if dead.
getName() - returns monster name (skeleton, orc, troll).
  >Skeleton
  >Orc
  >Troll
  
Weapon <<abstract>>
-damage
  >Mace
  >Sword
  >Axe
  
Item <<abstract>>
  >HealthPotion
  >Food


