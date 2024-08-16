/* 判断各个浏览器版本号 */
export const browserMatch = function matchVesion() {
    let userAgent = navigator.userAgent;
    let rMsie = /(msie\s|trident.*rv:)([\w.]+)/;
    let rEdge = /(edg)\/([\w.]+)/;
    let rFirefox = /(firefox)\/([\w.]+)/;
    let rOpera = /(opera).+version\/([\w.]+)/;
    let rChrome = /(chrome)\/([\w.]+)/;
    let rSafari = /version\/([\w.]+).*(safari)/;
    let ua = userAgent.toLowerCase();
    var match = rMsie.exec(ua);
    if (match !== null) {
        return {
            browser: "IE",
            version: match[2] || "0"
        };
    }
    var rEmatch = rEdge.exec(ua);
    if (rEmatch !== null) {
        return {
            browser: 'Edge',
            version: rEmatch[2] || "0"
        };
    }
    var rFmatch = rFirefox.exec(ua);
    if (rFmatch !== null) {
        return {
            browser: rFmatch[1] || "",
            version: rFmatch[2] || "0"
        };
    }
    var rOmatch = rOpera.exec(ua);
    if (rOmatch !== null) {
        return {
            browser: rOmatch[1] || "",
            version: rOmatch[2] || "0"
        };
    }
    var rCmatch = rChrome.exec(ua);
    if (rCmatch !== null) {
        return {
            browser: rCmatch[1] || "",
            version: rCmatch[2] || "0"
        };
    }
    var rSmatch = rSafari.exec(ua);
    if (rSmatch !== null) {
        return {
            browser: rSmatch[2] || "",
            version: rSmatch[1] || "0"
        };
    }
    if (match !== null) {
        return {
            browser: "",
            version: "0"
        };
    }
}


/**
 * Parse the time to string
 * @param {(Object|string|number)} time
 * @param {null} cFormat
 * @returns {string | null}
 */
export function parseTime(time) {
    if (!time) {
        return null;
    }

    let date;

    if (typeof time === 'object') {
        date = time;
    } else {
        if (typeof time === 'string') {
            if (/^[0-9]+$/.test(time)) {
                // Support timestamp in milliseconds
                time = parseInt(time);
            } else if (time.includes('T') && time.includes('+')) {
                // Parse ISO 8601 format with timezone information (e.g., 2024-04-22T04:00:03.000+0000)
                time = time.replace(/\.\d{3}/, ''); // Remove milliseconds if present
                const [datePart, timePart] = time.split('T');
                const [hourMinuteSecond, timezone] = timePart.split('+');
                time = `${datePart}T${hourMinuteSecond}+00:00`; // Convert to UTC
            } else {
                // Support Safari
                time = time.replace(/-/g, '/');
            }
        }

        if (typeof time === 'number' && time.toString().length === 10) {
            time = time * 1000;
        }

        date = new Date(time);
    }

    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes()
    };

    const time_str = `${formatObj.y}年${formatObj.m.toString().padStart(2, '0')}月${formatObj.d.toString().padStart(2, '0')}日 ${formatObj.h.toString().padStart(2, '0')}:${formatObj.i.toString().padStart(2, '0')}`;

    return time_str;
}


export const getWebSiteInfoValue = function (webSiteInfo, key) {
    return webSiteInfo ? (webSiteInfo[key] ? webSiteInfo[key].value : '') : ''
}