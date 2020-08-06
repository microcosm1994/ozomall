const domain = "http://localhost:8090"
let token = ''
wx.getStorage({
    key: 'token',
    success: function (res) {
        console.log(res)
        token = res.data
    }
})
const service = async (config) => {
    config['baseUrl'] = ''
    config['headers'] = {
        token: token
    }
    return new Promise((relove, reject) => {
        let data = {};
        if (config.method === "get") {
            data = config.params
        }
        if (config.method === "post") {
            data = config.data
        }
        console.log(wx)
        wx.request({
            url: domain + config.baseUrl + config.url,
            method: config.method,
            data: data,
            header: {
                ...config.headers,
                'Content-Type': 'application/json'
            },
            success: function (res) {
                relove(res)
            },
            fail: function (error) {
                reject(error)
            }
        });
    })
};


export default service