@web @priority3
Feature: Checkout di Demoblaze

  @negative
  Scenario Outline: User mencoba checkout dengan data tidak valid
    Given User membuka halaman Demoblaze
    When User menambahkan produk "Samsung galaxy s6" ke keranjang
    And User pergi ke halaman cart
    And User membuka form checkout
    And User mengisi form checkout dengan data berikut:
      | name   | country   | city    | card       | month | year  |
      | <name> | <country> | <city>  | <card>     | <month> | <year> |
    And User menekan tombol Purchase berdasarkan data validitas
    Then Akan muncul pesan "<expected_message>" sesuai hasil <isSuccess>

    Examples:
      | name  | country   | city    | card       | month | year  | isSuccess | expected_message                    |
      |       | Indonesia | Jakarta | 123456789  | 12    | 2025  | false     | Please fill out Name and Creditcard. |
      | Indah | Indonesia | Jakarta | abcd1234   | 12    | 2025  | true      | Thank you for your purchase!         |
      | Indah | Indonesia | Jakarta | 123456789  | 12    | 2020  | true      | Thank you for your purchase!         |

  @positive
  Scenario Outline: User berhasil checkout dengan data valid
    Given User membuka halaman Demoblaze
    When User menambahkan produk "<produk>" ke keranjang
    And User pergi ke halaman cart
    And User membuka form checkout
    And User mengisi form checkout dengan data berikut:
      | name   | country   | city    | card       | month | year  |
      | <name> | <country> | <city>  | <card>     | <month> | <year> |
    And User menekan tombol Purchase berdasarkan data validitas
    Then Order berhasil diproses dan muncul pesan sukses "Thank you for your purchase!"
    And User menutup pop-up konfirmasi pembelian

    Examples:
      | produk             | name  | country   | city    | card       | month | year  |
      | Samsung galaxy s6  | Indah | Indonesia | Jakarta | 123456789  | 12    | 2025  |
