package co.kr.nawa.simpleportfolio.menu.view


import android.app.Dialog
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityNfcBinding
import co.kr.nawa.simpleportfolio.menu.viewModel.NfcViewModel
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.dialog.DialogCustom
import co.kr.nawa.simpleportfolio.util.dialog.DialogItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.Arrays
import java.util.Locale


class NfcActivity : ActivityBase<ActivityNfcBinding, NfcViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_nfc
    override val viewModel: NfcViewModel by viewModel()
    private var nfcAdapter: NfcAdapter? = null
//    private var tag:Tag?=null
    private var checkWrite=false
    private val dialogCustom=DialogCustom(DialogItem("TAG 쓰기","TAG를 가져다 대세요!","")){

    }


    private val flags= NfcAdapter.FLAG_READER_NFC_A or
            NfcAdapter.FLAG_READER_NFC_B or
            NfcAdapter.FLAG_READER_NFC_F or
            NfcAdapter.FLAG_READER_NFC_V or
            NfcAdapter.FLAG_READER_NFC_BARCODE


    override fun init() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter != null) {
            //NFC off or NFC card Mode
            nfcOn()
        }else{
            //기기에서 NFC 있는지 확인
            finish()
            return
        }

        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 5000)

        nfcAdapter!!.enableReaderMode(this, {
//            tag=it
            if (checkWrite){
                write(it)
                dialogCustom.dismiss()
                return@enableReaderMode
            }


            val ndef = Ndef.get(it)

            val ndefMessage = ndef!!.cachedNdefMessage
            val records = ndefMessage!!.records

            for (rec in records) {

                if (rec.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(
                        rec.type,
                        NdefRecord.RTD_TEXT
                    )
                ) {
                    logD("ndefRecord=${rec}")
                    val offset=rec.payload[0].toInt() and 63
                    logD("msg=${String(rec.payload)}")

                    val str=String(rec.payload,offset+1,rec.payload.size -offset - 1,Charsets.UTF_8)
                    logD("msg11=${str}")
                    logD("bytes[0].toInt() and 63=${offset}")
                    logD("rec.payload.siz=${rec.payload.size}")
                    logD("rec.tnf=${rec.tnf}")
                    logD("rec.type=${rec.type}")
                    logD("rec.id=${rec.id}")

                    viewModel.setStr(str)
                }
            }


            //사용 테그 종류 확인용
//            for (item in it.techList){
//                logD("techList===${item.toString()}")
//            }
        },flags, options)

    }

    override fun init_Listener() {


        binding.writeBtn.setOnClickListener {

            if (binding.nfcText.text.toString().isEmpty()){
                snackbarShow("내용을 입력하세요.!!")
                return@setOnClickListener
            }
            checkWrite=true
            dialogCustom.show(supportFragmentManager,"writeBtn")
        }
    }

    private fun write(tag: Tag){

        val langBytes = Locale.KOREA.language.toByteArray(Charset.forName("US-ASCII"))
        val textBytes = binding.nfcText.text.toString().toByteArray(Charset.forName("UTF-8"))
        val status = (0 + langBytes.size).toChar()
        val data = ByteArray(1 + langBytes.size + textBytes.size)
        data[0] = status.code.toByte()
        System.arraycopy(langBytes, 0, data, 1, langBytes.size)
        System.arraycopy(textBytes, 0, data, 1 + langBytes.size, textBytes.size)


        val ndefRecord=NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), data)

        val ndef = Ndef.get(tag)

        ndef.connect()
        ndef.writeNdefMessage(NdefMessage(ndefRecord))
        ndef.close()

        checkWrite=false
        binding.nfcText.setText("")
        snackbarShow("입력 완료 되었습니다.!!")
    }


    override fun init_dataBinding() {
        viewModel.str.observe(this) {
            binding.contentTxt.text = it
        }
    }

    //intent-filte를 이용한 방법
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        logD("onNewIntent")
//        logD("action=${intent.action}")

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES,NfcAdapter::class.java)
//        }else{
//            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
//        }?.let { it ->
//            val messages: List<NdefMessage> = it.map { it as NdefMessage }
//
//
//            for (msg in messages){
//
//                for (rec in msg.records){
//
//                    if (rec.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(
//                            rec.type, NdefRecord.RTD_TEXT)
//                    ) {
//                        val offset=rec.payload[0].toInt() and 63
//                        logD("msg=${String(rec.payload)}")
//                        logD("msg_byteArrayToHexaString=${byteArrayToHexaString(rec.payload)}")
//                        val str=String(rec.payload,offset+1,rec.payload.size -offset - 1,Charsets.UTF_8)
//                        logD("msg11=${str}")
//                        logD("bytes[0].toInt() and 63=${offset}")
//                        logD("rec.payload.siz=${rec.payload.size}")
//                        logD("rec.tnf=${rec.tnf}")
//                        logD("rec.type=${rec.type}")
//                        logD("rec.id=${rec.id}")
//
//                        viewModel.setStr(str)
//                    }
//
//                }
//            }
//        }


//    }



    //nfc 무조건 활성화 시키기
    private fun nfcOn(){
        if(!nfcAdapter!!.isEnabled){
            DialogCustom(DialogItem("NFC 설정","NFC를 기본모드로 설정 해주세요!")){
                activityLauncher.launch(Intent(Settings.ACTION_NFC_SETTINGS))
            }.show(supportFragmentManager,"dialog")
        }

    }

    private val activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            _: ActivityResult ->
        //시스템 설정화면에 갔다가 왔을때는  RESULT_CANCELED 옴
        nfcOn()
    }

    override fun onStop() {
        super.onStop()
        nfcAdapter?.disableReaderMode(this)
    }
}