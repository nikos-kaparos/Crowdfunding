require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })
// PAIZEI GIA LOCAL OXI CONTAINER
// module.exports = {
//   devServer: {
//     "proxy": {
//       "/api/*": {
//         "target": "http://localhost:8080",
//       }
//     }
//   }
// };

module.exports = {
  devServer: {
    "proxy": {
      "/api/*": {
        "target": "http://localhost:8080",
      }
    }
  }
};

// require('@vue/cli-service')
// // module.exports = defineConfig({
// //   transpileDependencies: true
// // })


