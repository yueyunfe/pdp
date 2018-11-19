package edu.neu.ccs.cs5010.assignment4;

interface Visitor {

  String visit(Twix twix);

  String visit(Snickers snickers);

  String visit(Mars mars);

  String visit(KitKat kitKat);

  String visit(Whoopers whoopers);

  String visit(MilkyWay milkyWay);

  String visit(Toblerone toblerone);

  String visit(Crunch crunch);

  String visit(BabyRuth babyRuth);

  String visit(AlmondJoy almondJoy);
}
