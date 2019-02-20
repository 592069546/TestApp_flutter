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

  @override
  void initState(){
    super.initState();
    channel.setMessageHandler(receiveMessage);
  }

  Widget buildTextField(TextEditingController textController){
    return TextField(
      controller: textController,
      autofocus: true,
    );
  }

  @override
  Widget build(BuildContext context){
    return  Scaffold(
      appBar:  AppBar(
        title:  Text('第二个界面'),
      ),
      body: Center(
        child:TextField(),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _sendMessage,
        child: const Icon(Icons.add),
      ),
    );
  }

  void _sendMessage() {
    channel.send(textController.text);
    print('已发送');
  }

  Future<String> receiveMessage(String message) async {
    setState(() {

    });
    print(message);
    return message;
  }
}