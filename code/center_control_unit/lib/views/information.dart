import 'package:center_control_unit/views/test/test_index.dart';
import 'package:flutter/material.dart';

class Information extends StatelessWidget {
  const Information({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Center(
          child: Container(
            child: Text("Don't do any of that."),
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
          Navigator.of(context).push(
              MaterialPageRoute(
                  builder: (context) =>
                      TestIndex()));

          },
          child: const Icon(Icons.telegram),
        ),
      ),
    );
  }
}
