import React from 'react';
import cl from "../../../../styles/BidHeader.module.css";
import moment from "moment";
import {DATE_TIME_PATTERN} from "../../../../util/constants";

const BidHeader = ({auctionDetails, auctionComplete}) => {
    return (
        <div className={cl.bid__info__container}>
            {!auctionComplete
                ?
                <div className={cl.bid__info}>
                    <div className={cl.first__container}>
                        <p className={cl.label}>Текущая ставка:</p>
                        <p className={`${cl.content} ${cl.current__price}`}>{auctionDetails.auction.currentPrice} USD</p>
                    </div>
                    <div className={cl.second__container}>
                        <p className={cl.label}>Дата окончания:</p>
                        <p className={cl.content}>
                            {moment(auctionDetails.auction.endDate).format(DATE_TIME_PATTERN)}
                        </p>
                    </div>
                </div>
                :
                <div className={cl.bid__info}>
                    <div className={cl.first__container}>
                        <p className={cl.label}>Итоговая ставка:</p>
                        <p className={`${cl.content} ${cl.current__price}`}>{auctionComplete.totalPrice} USD</p>
                    </div>
                    <div className={cl.complete__message}>
                        <p>Аукцион завершен</p>
                    </div>
                </div>
            }

            <div className={cl.bid__info}>
                <div className={cl.first__container}>
                    <p className={cl.label}>Шаг ставки:</p>
                    <p className={`${cl.content} ${cl.price__step}`}>{auctionDetails.auction.priceStep} USD</p>
                </div>
                <div className={cl.second__container}>
                    <p className={cl.label}>Всего ставок:</p>
                    <p className={cl.content}>{auctionDetails.bids.length}</p>
                </div>
            </div>

        </div>
    );
};

export default BidHeader;