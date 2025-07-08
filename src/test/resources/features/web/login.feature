@web @priority2
Feature: Login ke Demoblaze

  @positive
  Scenario Outline: User berhasil login ke aplikasi
    Given user membuka halaman utama Demoblaze
    When user mengklik menu Log in
    And user memasukkan username "<username>" dan password "<password>"
    And user menekan tombol Log in
    Then sistem menampilkan halaman utama
    Examples:
      | username     | password  |
      | zahra_test   | test123 |

  @negative
  Scenario Outline: Gagal login dengan password salah
    Given user membuka halaman utama Demoblaze
    When user mengklik menu Log in
    And user memasukkan username "<username>" dan password "<password>"
    And user menekan tombol Log in
    Then sistem menampilkan pesan "<expectedMessage>"
    Examples:
      | username  | password   | expectedMessage   |
      | user123   | salah123   | Wrong password.   |

  @negative
  Scenario Outline: Gagal login dengan username yang belum terdaftar
    Given user membuka halaman utama Demoblaze
    When user mengklik menu Log in
    And user memasukkan username "<username>" dan password "<password>"
    And user menekan tombol Log in
    Then sistem menampilkan pesan "<expectedMessage>"
    Examples:
      | username   | password  | expectedMessage        |
      | tidak123   | test123   | User does not exist.   |
