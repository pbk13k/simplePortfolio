package co.kr.nawa.simpleportfolio.menu.view


import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.widget.Toast
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityNfcBinding
import co.kr.nawa.simpleportfolio.menu.viewModel.NfcViewModel
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.dialog.DialogCustom
import co.kr.nawa.simpleportfolio.util.dialog.DialogItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class NfcActivity : ActivityBase<ActivityNfcBinding, NfcViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_nfc
    override val viewModel: NfcViewModel by viewModel()
    private var nfcAdapter: NfcAdapter? = null

    override fun init() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter != null) {
            //NFC off or NFC card Mode
            if(!nfcAdapter!!.isEnabled){
                logD("nfc_off")
                DialogCustom(DialogItem("NFC 설정","NFC를 기본모드로 설정 해주세요!")){
                    logD("설정 gogo")
                }.show(supportFragmentManager,"dialog")
            }
        }else{
            //기기에서 NFC 있는지 확인
            finish()
            return

        }
    }

    override fun init_Listener() {
        binding.readBtn.setOnClickListener {

        }

        binding.writeBtn.setOnClickListener {

        }
    }


    override fun init_dataBinding() {
        viewModel.str.observe(this) {
            binding.contentTxt.text = it
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        logD("action=${intent.action}")

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                // Process the messages array.

            }
        }
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_nfc)
//    }
}