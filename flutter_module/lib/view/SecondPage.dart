import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';

class SecondPage extends StatefulWidget{
  @override
  SecondStatePage createState() => new SecondStatePage();
}

class SecondStatePage extends State<SecondPage>{
  static const String _channel = 'message';
  static const BasicMessageChannel<String> channel = BasicMessageChannel<String>(_channel, StringCodec());
  var textController = TextEditingController();

  String _message = "";

  @override
  void initState(){  //插入渲染树的时候调用，这个函数在生命周期中只调用一次
    super.initState();
    channel.setMessageHandler(_receiveMessage);
  }

//  Widget buildTextField(TextEditingController textController){
//    return TextField(
//      controller: textController,
//      autofocus: true,
//    );
//  }

  @override
  Widget build(BuildContext context){
    return  Scaffold(
//      appBar:  AppBar(
//        title:  Text('第二个界面'),
//      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          TextField(
            decoration: InputDecoration(
                border: OutlineInputBorder()
            ),
            controller: textController,
            autofocus: true,
          ),
          Text(
            _message ,
            softWrap: true,
          ),
        ]
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _sendMessage,
        child: const Icon(Icons.add),
      ),
    );
  }

  void _sendMessage() {
    channel.send(textController.text);
//    print('已发送' );
//    print(textController.text);
  }

  Future<String> _receiveMessage(String message) async {
    setState(() {
      _message = message;
    });
    _message = message;
//    print('已接收' );
    print("RxJava $message");
    return message;
  }
}