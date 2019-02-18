import 'package:flutter/material.dart';

class ThirdPage extends StatefulWidget{
  @override
  ThirdStatePage createState() => new ThirdStatePage();
}

class ThirdStatePage extends State<ThirdPage>{
  @override
  Widget build(BuildContext context){
    return new Scaffold(
        appBar: new AppBar(
          title: new Text('第三个界面'),
        ),
        body: new Center(
          child: new Text('界面3'),
        )
    );
  }
}