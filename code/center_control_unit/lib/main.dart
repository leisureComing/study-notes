import 'package:center_control_unit/config/global_context.dart';
import 'package:center_control_unit/provider/blue_provider.dart';
import 'package:center_control_unit/views/bluetooth.dart';
import 'package:center_control_unit/views/bottom_navigation.dart';
import 'package:center_control_unit/views/device.dart';
import 'package:center_control_unit/views/information.dart';
import 'package:center_control_unit/views/test/providers/blue_test_provider.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => BlueProvider()),
        ChangeNotifierProvider(create: (_) => BlueTestProvider()),
      ],
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // 保存全局context
    GlobalContext.context = context;
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      debugShowCheckedModeBanner: false,
      title: "Center Control unit",
      onGenerateRoute: onGenerateRoute,
      initialRoute: "/",
      routes: routes,
    );
  }
}

final routes = {
  "/": (context) => const BottomNavigation(),
  "/Bluetooth": (context) => const Bluetooth(),
  "/Information": (context) => const Information(),
  "/Device": (context) => const Device(),
};
// ignore: prefer_function_declarations_over_variables
var onGenerateRoute = (RouteSettings settings) {
  final String? name = settings.name;
  final Function? pageContentBuilder = routes[name];
  if (pageContentBuilder != null) {
    if (settings.arguments != null) {
      final Route route = MaterialPageRoute(
          builder: (context) =>
              pageContentBuilder(context, arguments: settings.arguments));
      return route;
    } else {
      //android风格的路由切换
      final Route route =
          MaterialPageRoute(builder: (context) => pageContentBuilder(context));
      return route;
    }
  }
};





/// ================测试===================
// void main() {
//   runApp(const MyApp());
// }
//
// class MyApp extends StatelessWidget {
//   const MyApp({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     // 定义的路由
//     final routes = {
//       "/": (context) => const Home(),
//       "/TargetPage": (context) => const TargetPage(),
//     };
//
//     return MaterialApp(
//       title: "路由测试",
//       onGenerateRoute: (RouteSettings settings) {
//         final String? name = settings.name;
//         final Function? pageContentBuilder = routes[name];
//         if (pageContentBuilder != null) {
//           if (settings.arguments != null) {
//             final Route route = MaterialPageRoute(
//                 builder: (context) =>
//                     pageContentBuilder(context, arguments: settings.arguments));
//             return route;
//           } else {
//             //android风格的路由切换
//             final Route route = MaterialPageRoute(
//                 builder: (context) => pageContentBuilder(context));
//             return route;
//           }
//         }
//       },
//       initialRoute: "/",
//       routes: routes,
//     );
//   }
// }
//
// /// home 页面
// /// 没有MaterialApp作为根组件
// class Home extends StatelessWidget {
//   const Home({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       body: Container(
//         width: double.infinity,
//         height: double.infinity,
//         padding: const EdgeInsets.all(20),
//         color: Colors.lightBlueAccent,
//         child: Column(
//           children: [
//             LeftPage(),
//             Container(
//               height: 300,
//               width: 300,
//               child: RightPage(),
//             )
//           ],
//         ),
//       ),
//     );
//   }
// }
//
// /// LeftPage
// /// 没有MaterialApp作为根组件
// class LeftPage extends StatelessWidget {
//   const LeftPage({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return Container(
//       color: Colors.amberAccent,
//       height: 300,
//       width: 300,
//       child: ElevatedButton(
//         onPressed: () {
//           // Navigator.of(context).push(
//           //     MaterialPageRoute(
//           //         builder: (context) =>
//           //             Device()));
//           //
//
//           Navigator.of(context).pushNamed("/TargetPage");
//         },
//         child: const Text("自身没MaterialApp的跳转"),
//       ),
//     );
//   }
// }
//
// /// RightPage
// /// MaterialApp作为根组件
// class RightPage extends StatelessWidget {
//   const RightPage({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     // 定义的路由
//     final routes = {
//       // "/": (context) => const Home(),
//       "/TargetPage": (context) => const TargetPage(),
//     };
//
//
//     return MaterialApp(
//       onGenerateRoute: (RouteSettings settings) {
//         final String? name = settings.name;
//         final Function? pageContentBuilder = routes[name];
//         if (pageContentBuilder != null) {
//           if (settings.arguments != null) {
//             final Route route = MaterialPageRoute(
//                 builder: (context) =>
//                     pageContentBuilder(context, arguments: settings.arguments));
//             return route;
//           } else {
//             //android风格的路由切换
//             final Route route = MaterialPageRoute(
//                 builder: (context) => pageContentBuilder(context));
//             return route;
//           }
//         }
//       },
//       routes: routes,
//       // initialRoute: "/",
//       home: ElevatedButton(
//         onPressed: () {
//           // Navigator.of(context)
//           //     .push(MaterialPageRoute(builder: (context) => TargetPage()));
//
//           Navigator.of(context).pushNamed("/TargetPage");
//         },
//         child: const Text("MaterialApp的跳转"),
//       ),
//     );
//   }
// }
//
// /// 没跳转的页面
// class TargetPage extends StatelessWidget {
//   const TargetPage({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: const Text('跳转的目标页面'),
//       ),
//     );
//   }
// }