import 'package:flutter/material.dart';

class SecondPage extends StatefulWidget{
  @override
  SecondStatePage createState() => new SecondStatePage();
}

class SecondStatePage extends State<SecondPage>{
  @override
  Widget build(BuildContext context){
    return new Scaffold(
        appBar: new AppBar(
          title: new Text('第二个界面'),
        ),
        body: new Center(
          child: new Text('界面2'),
        )
    );
  }
}