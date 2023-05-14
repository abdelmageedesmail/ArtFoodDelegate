package com.art4muslimt.artfooddelegate.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {


    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private List<DataEntity> data;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class DataEntity {
        @Expose
        @SerializedName("orderproduct")
        private List<OrderproductEntity> orderproduct;
        @Expose
        @SerializedName("distance_client_driver")
        private double distance_client_driver;
        @Expose
        @SerializedName("client_mobile")
        private String client_mobile;
        @Expose
        @SerializedName("distance_client_trader")
        private double distance_client_trader;
        @Expose
        @SerializedName("client_address")
        private String client_address;
        @Expose
        @SerializedName("client_lng")
        private String client_lng;
        @Expose
        @SerializedName("client_lat")
        private String client_lat;
        @Expose
        @SerializedName("clientname")
        private String clientname;
        @Expose
        @SerializedName("distance_driver_trader")
        private double distance_driver_trader;
        @Expose
        @SerializedName("trade_lng")
        private String trade_lng;
        @Expose
        @SerializedName("trade_lat")
        private String trade_lat;
        @Expose
        @SerializedName("tradename")
        private String tradename;
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("driver_status")
        private String driver_status;
        @Expose
        @SerializedName("driver_id")
        private String driver_id;
        @Expose
        @SerializedName("datestatus")
        private String datestatus;
        @Expose
        @SerializedName("trader_id")
        private String trader_id;
        @Expose
        @SerializedName("useraddress")
        private String useraddress;
        @Expose
        @SerializedName("hourto")
        private String hourto;
        @Expose
        @SerializedName("hourfrom")
        private String hourfrom;
        @Expose
        @SerializedName("deliverydate")
        private String deliverydate;
        @Expose
        @SerializedName("deliverytime")
        private String deliverytime;
        @Expose
        @SerializedName("paymentmethod")
        private String paymentmethod;
        @Expose
        @SerializedName("deliverymethod")
        private String deliverymethod;
        @Expose
        @SerializedName("youropinion")
        private String youropinion;
        @Expose
        @SerializedName("stars")
        private String stars;
        @Expose
        @SerializedName("transaction_id")
        private String transaction_id;
        @Expose
        @SerializedName("currency")
        private String currency;
        @Expose
        @SerializedName("note")
        private String note;
        @Expose
        @SerializedName("read")
        private String read;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("shipping")
        private String shipping;
        @Expose
        @SerializedName("totalprise")
        private String totalprise;
        @Expose
        @SerializedName("totalitem")
        private String totalitem;
        @Expose
        @SerializedName("user_id")
        private String user_id;
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("status_nameen")
        private String status_nameen;
        @Expose
        @SerializedName("status_name")
        private String status_name;
        @Expose
        @SerializedName("trade_mobile")
        private String trade_mobile;

        public String getTrade_mobile() {
            return trade_mobile;
        }

        public List<OrderproductEntity> getOrderproduct() {
            return orderproduct;
        }

        public void setOrderproduct(List<OrderproductEntity> orderproduct) {
            this.orderproduct = orderproduct;
        }

        public double getDistance_client_driver() {
            return distance_client_driver;
        }

        public void setDistance_client_driver(double distance_client_driver) {
            this.distance_client_driver = distance_client_driver;
        }

        public String getClient_mobile() {
            return client_mobile;
        }

        public void setClient_mobile(String client_mobile) {
            this.client_mobile = client_mobile;
        }

        public double getDistance_client_trader() {
            return distance_client_trader;
        }

        public void setDistance_client_trader(double distance_client_trader) {
            this.distance_client_trader = distance_client_trader;
        }

        public String getClient_address() {
            return client_address;
        }

        public void setClient_address(String client_address) {
            this.client_address = client_address;
        }

        public String getClient_lng() {
            return client_lng;
        }

        public void setClient_lng(String client_lng) {
            this.client_lng = client_lng;
        }

        public String getClient_lat() {
            return client_lat;
        }

        public void setClient_lat(String client_lat) {
            this.client_lat = client_lat;
        }

        public String getClientname() {
            return clientname;
        }

        public void setClientname(String clientname) {
            this.clientname = clientname;
        }

        public double getDistance_driver_trader() {
            return distance_driver_trader;
        }

        public void setDistance_driver_trader(double distance_driver_trader) {
            this.distance_driver_trader = distance_driver_trader;
        }

        public String getTrade_lng() {
            return trade_lng;
        }

        public void setTrade_lng(String trade_lng) {
            this.trade_lng = trade_lng;
        }

        public String getTrade_lat() {
            return trade_lat;
        }

        public void setTrade_lat(String trade_lat) {
            this.trade_lat = trade_lat;
        }

        public String getTradename() {
            return tradename;
        }

        public void setTradename(String tradename) {
            this.tradename = tradename;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getDriver_status() {
            return driver_status;
        }

        public void setDriver_status(String driver_status) {
            this.driver_status = driver_status;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getDatestatus() {
            return datestatus;
        }

        public void setDatestatus(String datestatus) {
            this.datestatus = datestatus;
        }

        public String getTrader_id() {
            return trader_id;
        }

        public void setTrader_id(String trader_id) {
            this.trader_id = trader_id;
        }

        public String getUseraddress() {
            return useraddress;
        }

        public void setUseraddress(String useraddress) {
            this.useraddress = useraddress;
        }

        public String getHourto() {
            return hourto;
        }

        public void setHourto(String hourto) {
            this.hourto = hourto;
        }

        public String getHourfrom() {
            return hourfrom;
        }

        public void setHourfrom(String hourfrom) {
            this.hourfrom = hourfrom;
        }

        public String getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(String deliverydate) {
            this.deliverydate = deliverydate;
        }

        public String getDeliverytime() {
            return deliverytime;
        }

        public void setDeliverytime(String deliverytime) {
            this.deliverytime = deliverytime;
        }

        public String getPaymentmethod() {
            return paymentmethod;
        }

        public void setPaymentmethod(String paymentmethod) {
            this.paymentmethod = paymentmethod;
        }

        public String getDeliverymethod() {
            return deliverymethod;
        }

        public void setDeliverymethod(String deliverymethod) {
            this.deliverymethod = deliverymethod;
        }

        public String getYouropinion() {
            return youropinion;
        }

        public void setYouropinion(String youropinion) {
            this.youropinion = youropinion;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public String getTotalprise() {
            return totalprise;
        }

        public void setTotalprise(String totalprise) {
            this.totalprise = totalprise;
        }

        public String getTotalitem() {
            return totalitem;
        }

        public void setTotalitem(String totalitem) {
            this.totalitem = totalitem;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus_nameen() {
            return status_nameen;
        }

        public void setStatus_nameen(String status_nameen) {
            this.status_nameen = status_nameen;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }
    }

    public static class OrderproductEntity {
        @Expose
        @SerializedName("addtions")
        private List<AddtionsEntity> addtions;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("category_nameen")
        private String category_nameen;
        @Expose
        @SerializedName("category_name")
        private String category_name;
        @Expose
        @SerializedName("cat_id")
        private String cat_id;
        @Expose
        @SerializedName("qty")
        private String qty;
        @Expose
        @SerializedName("prise")
        private String prise;
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public List<AddtionsEntity> getAddtions() {
            return addtions;
        }

        public void setAddtions(List<AddtionsEntity> addtions) {
            this.addtions = addtions;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCategory_nameen() {
            return category_nameen;
        }

        public void setCategory_nameen(String category_nameen) {
            this.category_nameen = category_nameen;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getPrise() {
            return prise;
        }

        public void setPrise(String prise) {
            this.prise = prise;
        }

        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class AddtionsEntity {
        @Expose
        @SerializedName("products")
        private List<ProductsEntity> products;
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("additions")
        private String additions;

        public List<ProductsEntity> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsEntity> products) {
            this.products = products;
        }

        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdditions() {
            return additions;
        }

        public void setAdditions(String additions) {
            this.additions = additions;
        }
    }

    public static class ProductsEntity {
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("price")
        private String price;
        @Expose
        @SerializedName("additionsdata")
        private String additionsdata;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAdditionsdata() {
            return additionsdata;
        }

        public void setAdditionsdata(String additionsdata) {
            this.additionsdata = additionsdata;
        }
    }
}
