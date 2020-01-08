# YarisFlight

# classes needed


runner (main function)

driver (handle i/o and top-level functions, keep track of current game state)

menuHandler (upgrade menus controlled by mouse)

gameplayHandler (screen during gameplay controlled by keyboard) (this will have all of the logic for the aero and the painting during gameplay)

endscreen (after dying)

# (random display classes)

button

displayBar (like a progress bar sorta deal)

HUD (heads up display, stats [fuel, altitude, distance] on screen during gameplay)


# entity

player

ramp (launcher thing at the start)

clouds (I guess)

obstacles(can be extended later)

particles (if we want)


# upgrade (contains data that affects player & ramp)

rampUpgrade (height and width?)

wingsUpgrade

engineUpgrade

payloadUpgrade (if we want to include obstacles to destroy)

