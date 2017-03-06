# Projet Android




Groupe : AFANGBOM Messan, HARIFI Hamza, KASMI Amina		                                                        		06/03/2017


# Cahier des charges



# Besoins fonctionnels

1. Charger une image :
(a) depuis la galerie :  méthodes openGallery(), puis onActivityResult()
                                     Icône -galerie- dans la toolbar
(b) depuis la camera : méthodes openCamera(), puis onActivityResult()
                                     Icône -appareil photo- dans la toolbar
Au moment de capturer la photo elle apparaît nette, mais une fois la photo prise elle devient floue puis s'affiche floue dans l'application.

2. Afficher une image : l'image choisie dans la galerie ou prise avec l'appareil photo s'affiche automatiquement dans l'application.

3. Zoomer : au prochain rendu

4. Scroller : au prochain rendu, on affiche l'image dans l'application de manière à ce qu'elle ne déborde pas de l'écran, une fois le zoom fait le scroll sera utile et donc rajouté.

5. Appliquer des filtres :

(a) Régler la luminosité : les méthodes luminosite (final Bitmap b) et saturation (final Bitmap b) appellent luminositeEtSaturation(int pourcentage, Bitmap b, String monchoix) ; onProgressChanged(SeekBar seekBar, int i, boolean fromUser), onStartTrackingTouch(SeekBar seekBar), onStopTrackingTouch(SeekBar seekBar) pour l'utilisation de la seekbar. La seekbar est lente, on ne sait pas pourquoi.

(b) Régler le contraste : méthode Contraste(Bitmap, bMap)

(c) Égalisation d'histogramme : méthode extensionDynamiqueGris(Bitmap bMap)

(d) Filtrage couleur : méthodes toGrayTableau(Bitmap bMap) pour mettre l'image en niveaux de gris, garderTeinte(String monchoix, Bitmap bMap) avec les couleurs rouge, vert, magenta, jaune et bleu ; teinte(int teinte, Bitmap b)pour la rotation HSV

(e) Convolution : méthodes convolution (Bitmap b, int [][] filtre, String monchoix) avec les filtres moyenne, gaussien, laplaciens, sobelPrewitt (Bitmap b, int [][] filtre1, int [][] filtre2) pour les filtres de Sobel et Prewitt. Les bords des images ne sont pas traités.

6. Réinitialiser : pas de méthode particulière : en récupérant l’image initiale on la stocke dans une bitmap, que l’on remet avec la méthode setImageToBitmap() 
                        Icône -poubelle- dans la toolbar

7. Sauvegarder une image : au prochain rendu. Icône -save- dans la toolbar     


Tous les filtres marchent sur l'image « Lena » chargée depuis la galerie.
Tous les filtres fonctionnent sur les photos qui sont prises directement depuis l'appareil photo.
Pour les photos prises par l'appareil photo mais qui sont déjà dans la galerie, les filtres contraste, luminosité, garderTeinte et les convolutions ne fonctionnent pas.
Sur certaines photos les convolutions marchent mais que sur la partie supérieure de la photo. Nous suspectons un problème de mémoire, car les images qui sont dans la galerie sont trop lourdes pour l’application.



# Fonctionnalités supplémentaires réalisées :
Design : Toolbar, couleur, fond de l'application, icône de l'application
Menu dans la toolbar pour accéder aux différents filtres : méthodes onCreateOptionsMenu(Menu menu), onOptionsItemSelected(MenuItem item)
Filtre négatif qui inverse les couleurs d'une image avec la méthode negatif (Bitmap bMap)
Mettre un fond d’écran avec la méthode startWall(Bitmap bMap)


# Fonctionnalités supplémentaires qui nous semblent réalisables dans le temps restant :
1. Simuler un effet dessin au crayon
2. Simuler un effet cartoon
