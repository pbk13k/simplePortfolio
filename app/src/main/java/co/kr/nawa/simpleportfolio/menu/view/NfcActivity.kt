package co.kr.nawa.simpleportfolio.menu.view


import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Build
import android.provider.Settings
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
import java.util.Arrays


class NfcActivity : ActivityBase<ActivityNfcBinding, NfcViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_nfc
    override val viewModel: NfcViewModel by viewModel()
    private var nfcAdapter: NfcAdapter? = null


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

    }

    override fun init_Listener() {


        binding.writeBtn.setOnClickListener {
            logD("aa")
        }
    }


    override fun init_dataBinding() {
        viewModel.str.observe(this) {
            binding.contentTxt.text = it
        }
    }

    private fun byteArrayToHexaString(bytes: ByteArray): String? {
        val builder = StringBuilder()
        for (data in bytes) {
            builder.append(String.format("%02X ", data))
        }
        return builder.toString()
    }

    override fun onNewIntent(intent: Intent) {
        //setIntent(intent)
        logD("onNewIntent")
        logD("action=${intent.action}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES,NfcAdapter::class.java)
        }else{
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        }?.let { it ->
            val messages: List<NdefMessage> = it.map { it as NdefMessage }


            for (msg in messages){

                for (rec in msg.records){

                    if (rec.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(
                            rec.type, NdefRecord.RTD_TEXT)
                    ) {
                        val offset=rec.payload[0].toInt() and 63
                        logD("msg=${String(rec.payload)}")
                        logD("msg_byteArrayToHexaString=${byteArrayToHexaString(rec.payload)}")
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
            }
        }

        super.onNewIntent(intent)
    }



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
}