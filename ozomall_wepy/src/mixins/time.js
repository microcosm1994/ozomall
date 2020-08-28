export default {
    diffTime(time) {
        let dateTimeStamp = new Date(time).getTime();
        let now = new Date().getTime();
        let minute = 1000 * 60;
        let hour = minute * 60;
        let day = hour * 24;
        let month = day * 30;
        let diffVal = now - dateTimeStamp;
        if (diffVal < 0) {
            return '刚刚';
        }
        let monthC = diffVal / month;
        let weekC = diffVal / (7 * day);
        let dayC = diffVal / day;
        let hourC = diffVal / hour;
        let minC = diffVal / minute;
        if (monthC >= 1) {
            return parseInt(monthC) + '个月前';
        } else if (weekC >= 1) {
            return parseInt(weekC) + '周前';
        } else if (dayC >= 1) {
            return parseInt(dayC) + '天前';
        } else if (hourC >= 1) {
            return parseInt(hourC) + '小时前';
        } else if (minC >= 1) {
            return parseInt(minC) + '分钟前';
        } else return '刚刚';
    }
}