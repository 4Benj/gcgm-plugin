import React from 'react';

export function ReactIsInDevelopmentMode() { 
    return '_self' in React.createElement('div');
}

export function formatBytesToString(bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes';

    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}

export function bytesToMegabytes(bytes, decimals = 2) {
    const dm = decimals < 0 ? 0 : decimals;

    const k = 1024;
    return parseFloat((bytes / Math.pow(k, 2)).toFixed(dm));
}