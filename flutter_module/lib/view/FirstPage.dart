import 'package:flutter/material.dart';
import '../component/list.dart';

class FirstPage extends StatefulWidget{
  @override
  FirstStatePage createState() => new FirstStatePage();
}

class FirstStatePage extends State<FirstPage>{
  @override
  Widget build(BuildContext context){
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('第一个界面'),
      ),
      body: new Center(
        child: new List(),
    )
    );
  }
}