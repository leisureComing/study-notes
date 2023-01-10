import 'package:flutter/material.dart';

class Device extends StatefulWidget {
  const Device({Key? key}) : super(key: key);

  @override
  State<Device> createState() => _DeviceState();
}

class _DeviceState extends State<Device> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: const Text('设备扫描结果'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: List.generate(
              4,
              (index) {
                return ExpansionTile(
                  title: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text('天线$index'),
                      Text(
                        'AGHHAAFFADDSFASDF',
                        style: Theme.of(context).textTheme.bodyText2?.copyWith(
                            color: Theme.of(context).textTheme.caption?.color),
                      ),
                    ],
                  ),
                  children: [
                    ListTile(
                      title: Text('属性1'),
                      subtitle: Text("30"),
                      trailing: IconButton(
                        icon: Icon(
                          Icons.edit,
                          color: Theme.of(context)
                              .iconTheme
                              .color
                              ?.withOpacity(0.5),
                        ),
                        onPressed: () {},
                      ),
                    ),
                    ListTile(
                      title: Text('属性2'),
                      subtitle: Text("30"),
                      trailing: IconButton(
                        icon: Icon(
                          Icons.edit,
                          color: Theme.of(context)
                              .iconTheme
                              .color
                              ?.withOpacity(0.5),
                        ),
                        onPressed: () {},
                      ),
                    ),
                    ListTile(
                      title: Text('属性3'),
                      subtitle: Text("30"),
                      trailing: IconButton(
                        icon: Icon(
                          Icons.edit,
                          color: Theme.of(context)
                              .iconTheme
                              .color
                              ?.withOpacity(0.5),
                        ),
                        onPressed: () {},
                      ),
                    ),
                  ],
                );
              },
            ),
          ),
        ),
      ),
    );
  }
}
