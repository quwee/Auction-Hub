import React, {useEffect, useState} from 'react';
import cl from "../../../../styles/BidPlace.module.css";
import checkPriceInput from "../../../../util/checkPrice";
import {useAuthContext} from "../../../../context/context";

const BidPlace = ({auctionDetails, auctionComplete, placeBidCallback, completeCallback, showToolTipCallBack}) => {

    const {user} = useAuthContext()
    const [price, setPrice] = useState('')
    const [minPrice, setMinPrice] = useState(0)
    const [priceError, setPriceError] = useState(false)

    const getMinPrice = () => {
        if (auctionDetails.bids.length !== 0) {
            return auctionDetails.auction.currentPrice + auctionDetails.auction.priceStep
        }
        return auctionDetails.auction.currentPrice
    }

    useEffect(() => {
        setMinPrice(getMinPrice())
    }, [auctionDetails])

    const validatePrice = () => {
        let isError = false

        if (price < minPrice) {
            setPriceError(true)
            isError = true
        } else {
            setPriceError(false)
        }
        return isError
    }

    const handlePlaceBidClick = () => {
        let isError = validatePrice()
        if (isError) {
            return
        }
        if (!user) {
            showToolTipCallBack()
            return
        }
        let isDelivered = placeBidCallback(price)

        if (isDelivered) {
            setPrice('')
        }
    }

    const handleCancelBidClick = () => {
        completeCallback()
    }

    return (
        !auctionComplete
            ?
            <div className={cl.bid__place__container}>
                <div className={cl.bid__place__input__container}>
                    <input
                        className={`${cl.bid__place__elem} ${cl.bid__place__input}`}
                        value={price}
                        onChange={e => checkPriceInput(e, 6, setPrice)}
                        placeholder={String(minPrice)}
                    />
                    <span className={cl.bid__place__input__currency}>
                        USD
                    </span>
                </div>
                <p className={cl.bid__place__input__error}
                   hidden={!priceError}>
                    Вы можете поставить как минимум {minPrice} USD
                </p>
                {user && auctionDetails.auction.ownerId === user.id
                    ?
                    <button
                        className={`${cl.bid__place__elem} ${cl.bid__cancel__button}`}
                        onClick={handleCancelBidClick}>
                        Завершить аукцион
                    </button>
                    :
                    <button
                        className={`${cl.bid__place__elem} ${cl.bid__place__button}`}
                        onClick={handlePlaceBidClick}>
                        Сделать ставку
                    </button>
                }

            </div>
            :
            auctionComplete.winnerId !== 0
                ?
                <div className={cl.complete__container}>
                    <p className={cl.complete__label}>Победитель</p>
                    <p className={cl.complete__winner}>
                        {`${auctionComplete.firstName} ${auctionComplete.lastName}`}
                    </p>
                </div>
                :
                <div className={cl.complete__container}>
                    <p className={cl.complete__winner}>Победителя нет</p>
                </div>
    );
};

export default BidPlace;