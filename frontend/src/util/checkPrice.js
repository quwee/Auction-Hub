const checkPriceInput = (e, maxDigits, setInputValue) => {
    let v = e.target.value
    if (v.charAt(0) === '0') {
        setInputValue('')
        return
    }
    if (v.length > maxDigits) {
        return
    }
    v = v.replace(/[^\d]/g, '')
    setInputValue(v)
};

export default checkPriceInput;