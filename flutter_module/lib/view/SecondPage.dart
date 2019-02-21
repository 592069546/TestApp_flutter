import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';

class SecondPage extends StatefulWidget{
  @override
  SecondStatePage createState() => new SecondStatePage();
}

class SecondStatePage extends State<SecondPage>{
  static const String _channel = 'message';
  static const String _pong = 'pong';
  static const BasicMessageChannel<String> channel = BasicMessageChannel<String>(_channel, StringCodec());
  var textController = TextEditingController();

  String message;

  @override
  void initState(){
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
      appBar:  AppBar(
        title:  Text('第二个界面'),
      ),
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
            this.message == null ? '' : this.message,
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
      this.message = message;
    });
    this.message = message;
//    print('已接收' );
//    print(message);
    return message;
  }
}