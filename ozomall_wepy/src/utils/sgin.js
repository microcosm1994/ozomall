import md5 from "./md5.js";
console.log(getRandomArbitrary(0, 36))
// 随机字符串
export function getRandomStr(len) {
    let base = "abcdefghijklmnopqrstuvwxyz0123456789";
    let str = ''
    for (let i = 0; i < len; i++) {
        str += base[getRandomArbitrary(0, 36)]
    }
    return str;
}

// 生成签名字符串
export function getSign(data) {
    let keyArr = Object.keys(data).sort()
    let stringA = ''
    for (let i = 0; i < keyArr.length; i++) {
        let key = keyArr[i]
        if (data[key] + '') {
            stringA += key + '=' + data[key] + '&'
        }
    }
    stringA = stringA + "&key=192006250b4c09247ec02edce69f6a2d" //注：key为商户平台设置的密钥key
    return md5.md5(stringA).toString().toUpperCase()
}

function getRandomArbitrary(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
}