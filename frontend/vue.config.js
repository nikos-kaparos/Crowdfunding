require('@vue/cli-service')

// module.exports = {
//   devServer: {
//     proxy: {
//       '/api': {
//         target: 'https://TO-BACKEND-SOU.run.app', // Βάλε εδώ το URL του Cloud Run
//         changeOrigin: true,
//         logLevel: 'debug', // Για να βλέπεις στο τερματικό τι γίνεται
//         pathRewrite: { '^/api': '' }, // (Προαιρετικό - δες εξήγηση παρακάτω)
//       },
//     },
//   },
// };

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
// Change in 12-6-25 
module.exports = {
  devServer: {
    "proxy": {
      "/api/*": {
        "target": "http://spring_app:8080",
      }
    }
  }
};

// 12-6-25
// Working google bucket the frontend is up 
// 1 npm build
// 2 gsutil rsync -r dist/ gs://frontend-bucket-test-1
// gs: is the ulr of bucket that create from GOOGLE STORAGE
// module.exports = {
//   publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
// };







