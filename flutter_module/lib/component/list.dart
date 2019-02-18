import 'package:flutter/material.dart';

class List extends StatefulWidget{
  @override
  ListState createState() => ListState();
}

class ListState extends State<List>{
  @override
  Widget build(BuildContext context){
    return ListView.builder(
      itemCount: 10,
      itemBuilder: (BuildContext context, int index){
        return Card(
          child: Container(
            padding: EdgeInsets.all(10.0),
            child: ListTile(
              subtitle: Container(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("标题", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16.0),),
                      ],
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children:[
                            Text("time"),
                            Text("xxxx-xx-xx xx:xx"),
                          ]
                        ),
                      ],
                    ),
                    Row(
                      children: <Widget>[
                        Container(
                          padding: const EdgeInsets.fromLTRB(0.0, 8.0, 0.0, 2.0),
                          child: Text("内容"),
                        )
                      ],
                    ),
                  ],
                ),
              ),
              trailing: Icon(Icons.keyboard_arrow_right, color: Colors.grey), //显示右侧箭头，不显示则传null
            ),
          ),
        );
      },
    );
  }
}