package jp.sourceforge.andjong;
import static jp.sourceforge.andjong.Hai.*;
import static jp.sourceforge.andjong.Tehai.JYUNTEHAI_MAX;
import jp.sourceforge.andjong.Tehai.Combi;

public class Yaku {
	static boolean checkTanyao(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getId();
			//数牌でなければ不成立
			if ((id & KIND_SHUU) == 0)
				return false;
			id &= KIND_MASK;
			//数が1か9ならば不成立
			if ((id == 1) || (id == 9))
				return false;
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getId();
			id &= KIND_MASK;
			//123 と　789 の順子があれば不成立
			if ((id == 1) || (id == 7))
				return false;
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			if ((id & KIND_SHUU) == 0)
				return false;
			id &= KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			if ((id & KIND_SHUU) == 0)
				return false;
			id &= KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getId();
			if ((id & KIND_SHUU) == 0)
				return false;
			id &= KIND_MASK;
			if ((id == 1) || (id == 9))
				return false;
		}
		
		return true;
	}

	static boolean checkPinfu(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		//鳴きが入っている場合は成立しない
		if(tehai.getJyunTehaiLength() < JYUNTEHAI_MAX){
			return false;
		}
		
		//面子が順子だけではない
		if(combi.shunCount != 4){
			return false;
		}
		
		//頭が三元牌 
		id = combi.atamaId;
		if( (id & KIND_SANGEN) != 0){
			return false;
		}
		
		//頭が風牌
		if( (id & KIND_FON) != 0){
			//if()//TODO 場風もしくは自風ならばfalse	
				return false;
		}
		
		//待ちが両面待ちか
		//TODO 両面待ちかどうか判定
		
		//条件を満たしていれば、約成立
		return true;
	}

	static boolean checkIpeikou(Tehai tehai, Hai addHai, Combi combi) {

		//鳴きが入っている場合は成立しない
		if(tehai.getJyunTehaiLength() < JYUNTEHAI_MAX){
			return false;
		}
		
		//順子の組み合わせを確認する
		for (int i = 0; i < combi.shunCount -1; i++) {
			if(combi.shunIds[i] == combi.shunIds[i+1]){
				return true;
			}
		}
		
		return false;
	}
	//TODO リーチや一発系の役
	/*
	static boolean checkReach(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}

	static boolean checkIppatu(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}

	static boolean checkTumo(Tehai tehai, Hai addHai, Combi combi) {
		//鳴きが入っている場合は成立しない
		if(tehai.getJyunTehaiLength() < JYUNTEHAI_MAX){
			return false;
		}
		
		return true;
	}
*/

	//役牌ができているかどうかの判定に使う補助メソッド
	private static boolean checkYakuHai(Tehai tehai, Combi combi , int yakuHaiId) {
		int id;
		Hai checkHai[][]; 

		//純手牌をチェック
		for(int i = 0; i < combi.kouCount ; i++){
			//IDと役牌のIDをチェック
			if( combi.kouIds[i] == yakuHaiId ){
				return true;
			}
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			//IDと役牌のIDをチェック
			if( id == yakuHaiId ){
				return true;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			//IDと役牌のIDをチェック
			if( id == yakuHaiId ){
				return true;
			}
		}
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getId();
			//IDと役牌のIDをチェック
			if( id == yakuHaiId ){
				return true;
			}
		}
		return false;
	}
	
	static boolean checkTon(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_TON);
	}

	static boolean checkNan(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_NAN);
	}

	static boolean checkSya(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_SYA);
	}

	static boolean checkPei(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_PEE);
	}

	static boolean checkHaku(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_HAKU);
	}

	static boolean checkHatu(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_HATU);
	}

	static boolean checkCyun(Tehai tehai, Hai addHai, Combi combi) {
		return checkYakuHai(tehai,combi,KIND_CYUN);
	}
	
	//TODO 特殊な役は後回し
/*
	static boolean checkHaitei(Tehai tehai, Hai addHai, Combi combi) {

		return true;
	}
	
	static boolean checkRinsyan(Tehai tehai, Hai addHai, Combi combi) {

		return true;
	}
	static boolean checkCyankan(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}
	
	static boolean checkDoubleReach(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}
	
	static boolean checkTeetoitu(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}
*/
	
	static boolean checkCyanta(Tehai tehai, Hai addHai, Combi combi) {
		
		int id;
		Hai checkHai[][]; 

		//純手牌の刻子をチェック
		for(int i = 0; i < combi.kouCount ; i++){
			id = combi.kouIds[i];
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
			
		}
		
		//純手牌の順子をチェック
		for(int i = 0; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 7))
					return false;
			}			
		}
		
		//純手牌の頭をチェック
		id = combi.atamaId;
		if ((id & KIND_SHUU) != 0){
			id &= KIND_MASK;
			if ((id > 1) && (id < 9))
				return false;
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getId();
			id &= KIND_MASK;
			//123 と　789 以外の順子があれば不成立
			if ((id > 1) && (id < 7))
				return false;
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getId();
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		return true;
	}

	static boolean checkIkkituukan(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai checkHai[][]; 
		boolean ikkituukanflg[]= {false,false,false,false,false,false,false,false,false};
		//萬子、筒子、索子の1,4,7をチェック
		int checkId[] = {KIND_WAN+1,KIND_WAN+4,KIND_WAN+7,KIND_PIN+1,KIND_PIN+4,KIND_PIN+7,KIND_SOU+1,KIND_SOU+4,KIND_SOU+7};
		
		//手牌の順子をチェック
		for(int i = 0 ; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			for(int j =0 ; j < checkId.length ; j++){
				if(id == checkId[j]){
					ikkituukanflg[j] = true;
				}
			}
		}
		
		//鳴いた牌をチェック
		for(int i = 0 ; i < tehai.getMinshunsLength() ; i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getId();
			for(int j =0 ; j < checkId.length ; j++){
				if(id == checkId[j]){
					ikkituukanflg[j] = true;
				}
			}
		}
		
		//一気通貫が出来ているかどうかチェック
		if(   (ikkituukanflg[0] == true && ikkituukanflg[1] == true && ikkituukanflg[2] == true )
			||(ikkituukanflg[3] == true && ikkituukanflg[4] == true && ikkituukanflg[5] == true )
			||(ikkituukanflg[6] == true && ikkituukanflg[7] == true && ikkituukanflg[8] == true )){
			return true;
		}else{
			return false;
		}
	}

	//三色ができているかどうかの判定に使う補助メソッド
	private static void checkSansyoku(int id , boolean sansyokuflg[][]){
		//萬子、筒子、索子をチェック
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};
		for(int i =0 ; i < sansyokuflg.length ; i++){
			for(int j = 0; j < sansyokuflg[i].length ; j++){
				if(id == (checkId[i] + j+1) ){
					sansyokuflg[i][j] = true;
				}
			}
		}		
	}
	
	static boolean checkSansyokuDoujun(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai checkHai[][]; 
		boolean sansyokuflg[][]= new boolean[3][9];
		
		
		//フラグの初期化
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}
		
		//手牌の順子をチェック
		for(int i = 0 ; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			checkSansyoku(id,sansyokuflg);
		}
		
		//鳴いた牌をチェック
		for(int i = 0 ; i < tehai.getMinshunsLength() ; i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getId();
			checkSansyoku(id,sansyokuflg);
		}
		
		//三色同順が出来ているかどうかチェック
		for(int i = 0 ; i < sansyokuflg[0].length ; i++){
			if( (sansyokuflg[0][i] == true) && (sansyokuflg[1][i] == true ) && (sansyokuflg[2][i] == true)){
				return true;
			}
		}
		//出来ていない場合 falseを返却
		return false;
	}

	static boolean checkSansyokuDoukou(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai checkHai[][]; 
		boolean sansyokuflg[][]= new boolean[3][9];
		
		
		//フラグの初期化
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}
		
		//手牌の刻子をチェック
		for(int i = 0 ; i < combi.kouCount ; i++){
			id = combi.kouIds[i];
			checkSansyoku(id,sansyokuflg);
		}
		
		//鳴いた牌の明刻をチェック
		for(int i = 0 ; i < tehai.getMinkousLength() ; i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			checkSansyoku(id,sansyokuflg);
		}
		
		//鳴いた牌の明槓をチェック
		for(int i = 0 ; i < tehai.getMinkansLength() ; i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			checkSansyoku(id,sansyokuflg);
		}

		//鳴いた牌の暗槓をチェック
		for(int i = 0 ; i < tehai.getAnkansLength() ; i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getId();
			checkSansyoku(id,sansyokuflg);
		}
		
		//三色同順が出来ているかどうかチェック
		for(int i = 0 ; i < sansyokuflg[0].length ; i++){
			if( (sansyokuflg[0][i] == true) && (sansyokuflg[1][i] == true ) && (sansyokuflg[2][i] == true)){
				return true;
			}
		}
		
		//出来ていない場合 falseを返却
		return false;
	}
	
	static boolean checkToitoi(Tehai tehai, Hai addHai, Combi combi) {
		//手牌に順子がある
		if((combi.shunCount != 0) || (tehai.getMinshunsLength() != 0) ){
			return false;
		}else{
			return true;
		}
	}
	
	static boolean checkSanankou(Tehai tehai, Hai addHai, Combi combi) {
		//手牌の暗刻が３つ
		//TODO 上がった際が出上がりかどうかの判定も必要？
		if(combi.kouCount == 3){
			return true;
		}else{
			return true;
		}
	}
	
	static boolean checkSankantu(Tehai tehai, Hai addHai, Combi combi) {
		int kansnumber = 0;
		kansnumber += tehai.getAnkansLength();
		kansnumber += tehai.getMinkansLength();
		if(kansnumber == 3){
			return true;
		}else{
			return false;
		}
	}

	static boolean checkRyanpeikou(Tehai tehai, Hai addHai, Combi combi) {
		//鳴きが入っている場合は成立しない
		if(tehai.getJyunTehaiLength() < JYUNTEHAI_MAX){
			return false;
		}
		
		//順子が４つである
		if(combi.shunCount < 4){
			return false;
		}
		
		//順子の組み合わせを確認する
		if(combi.shunIds[0] == combi.shunIds[1]
		&& combi.shunIds[2] == combi.shunIds[3]){
			return true;
		}else{
			return false;
		}
	}
	
	static boolean checkHonitu(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//萬子、筒子、索子をチェック
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean honituflg = true;
			//純手牌をチェック
			int jyunTehaiLength = tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				id = jyunTehai[j].getId();
				//牌が(萬子、筒子、索子)もしくは字牌
				if (((id & checkId[i]) != 0) || ((id & KIND_TSUU) != 0) ){
					honituflg = false;
				}
			}
			
			//明順の牌をチェック
			for(int j = 0; j < tehai.getMinshunsLength(); j++){
				checkHai = tehai.getMinshuns();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)もしくは字牌
				if (((id & checkId[i]) != 0) || ((id & KIND_TSUU) != 0) ){
					honituflg = false;
				}
			}
			
			//明刻の牌をチェック
			for(int j = 0; j < tehai.getMinkousLength(); j++){
				checkHai = tehai.getMinkous();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)もしくは字牌
				if (((id & checkId[i]) != 0) || ((id & KIND_TSUU) != 0) ){
					honituflg = false;
				}
			}
			
			//明槓の牌をチェック
			for(int j = 0; j < tehai.getMinkansLength(); j++){
				checkHai = tehai.getMinkans();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)もしくは字牌
				if (((id & checkId[i]) != 0) || ((id & KIND_TSUU) != 0) ){
					honituflg = false;
				}
			}
			
			//暗槓の牌をチェック
			for(int j = 0; j < tehai.getAnkansLength(); j++){
				checkHai = tehai.getAnkans();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)もしくは字牌
				if (((id & checkId[i]) != 0) || ((id & KIND_TSUU) != 0) ){
					honituflg = false;
				}
			}
			
			//混一が成立している
			if(honituflg == true){
				return true;
			}
			
		}
		//成立していなけらば不成立
		return false;

	}
	
	static boolean checkJunCyan(Tehai tehai, Hai addHai, Combi combi) {
		
		int id;
		Hai checkHai[][]; 
		
		//純手牌の刻子をチェック
		for(int i = 0; i < combi.kouCount ; i++){
			id = combi.kouIds[i];
			//字牌があれば不成立
			if( (id & KIND_TSUU) != 0){
				return false;
			}

			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//純手牌の順子をチェック
		for(int i = 0; i < combi.shunCount ; i++){
			id = combi.shunIds[i];
			//字牌があれば不成立
			if( (id & KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 7))
					return false;
			}			
		}
		
		//純手牌の頭をチェック
		id = combi.atamaId;
		//字牌があれば不成立
		if( (id & KIND_TSUU) != 0){
			return false;
		}
		if ((id & KIND_SHUU) != 0){
			id &= KIND_MASK;
			if ((id > 1) && (id < 9))
				return false;
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getId();
			id &= KIND_MASK;
			//123 と　789 以外の順子があれば不成立
			if ((id > 1) && (id < 7))
				return false;
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			//字牌があれば不成立
			if( (id & KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			//字牌があれば不成立
			if( (id & KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getId();
			//字牌があれば不成立
			if( (id & KIND_TSUU) != 0){
				return false;
			}
			//数牌の場合は数字をチェック
			if ((id & KIND_SHUU) != 0){
				id &= KIND_MASK;
				if ((id > 1) && (id < 9))
					return false;
			}
		}
		
		return true;
	}
	
	static boolean checkSyousangen(Tehai tehai, Hai addHai, Combi combi) {
		//三元牌役が成立している個数を調べる
		int countSangen = 0;
		//白が刻子
		if(checkHaku(tehai,addHai,combi) == true){
			countSangen++;
		}
		//発が刻子
		if(checkHatu(tehai,addHai,combi) == true){
			countSangen++;
		}
		//中が刻子
		if(checkCyun(tehai,addHai,combi) == true){
			countSangen++;
		}
		//頭が三元牌 かつ、三元牌役が2つ成立
		if(((combi.atamaId & KIND_SANGEN) != 0) && (countSangen == 2)){
			return true;
		}
		
		return false;
	}
	
	static boolean checkHonroutou(Tehai tehai, Hai addHai, Combi combi) {
		//トイトイが成立している
		if(checkToitoi(tehai,addHai,combi) == false){
			return false;
		}
		
		//チャンタが成立している
		if(checkCyanta(tehai,addHai,combi) == true){
			return true;
		}else{
			return false;
		}
	}
/*	
	static boolean checkRenhou(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}
*/
	static boolean checkTinitu(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//萬子、筒子、索子をチェック
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean honituflg = true;
			//純手牌をチェック
			int jyunTehaiLength = tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				id = jyunTehai[j].getId();
				//牌が(萬子、筒子、索子)
				if (((id & checkId[i]) != 0)){
					honituflg = false;
				}
			}
			
			//明順の牌をチェック
			for(int j = 0; j < tehai.getMinshunsLength(); j++){
				checkHai = tehai.getMinshuns();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)
				if (((id & checkId[i]) != 0)){
					honituflg = false;
				}
			}
			
			//明刻の牌をチェック
			for(int j = 0; j < tehai.getMinkousLength(); j++){
				checkHai = tehai.getMinkous();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)
				if (((id & checkId[i]) != 0)){
					honituflg = false;
				}
			}
			
			//明槓の牌をチェック
			for(int j = 0; j < tehai.getMinkansLength(); j++){
				checkHai = tehai.getMinkans();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)
				if (((id & checkId[i]) != 0)){
					honituflg = false;
				}
			}
			
			//暗槓の牌をチェック
			for(int j = 0; j < tehai.getAnkansLength(); j++){
				checkHai = tehai.getAnkans();
				id = checkHai[j][0].getId();
				//牌が(萬子、筒子、索子)
				if (((id & checkId[i]) != 0)){
					honituflg = false;
				}
			}
			
			//清一が成立している
			if(honituflg == true){
				return true;
			}
			
		}
		//成立していなければ不成立
		return false;

	}

	static boolean checkSuuankou(Tehai tehai, Hai addHai, Combi combi) {
		//手牌の暗刻が4つ
		//TODO 上がった際が出上がり、ツモ上がりかどうかの判定も必要？
		if(combi.kouCount == 4){
			return true;
		}else{
			return true;
		}
	}

	static boolean checkSuukantu(Tehai tehai, Hai addHai, Combi combi) {
		int kansnumber = 0;
		kansnumber += tehai.getAnkansLength();
		kansnumber += tehai.getMinkansLength();
		if(kansnumber == 4){
			return true;
		}else{
			return false;
		}
	}

	static boolean checkDaisangen(Tehai tehai, Hai addHai, Combi combi) {
		//三元牌役が成立している個数を調べる
		int countSangen = 0;
		//白が刻子
		if(checkHaku(tehai,addHai,combi) == true){
			countSangen++;
		}
		//発が刻子
		if(checkHatu(tehai,addHai,combi) == true){
			countSangen++;
		}
		//中が刻子
		if(checkCyun(tehai,addHai,combi) == true){
			countSangen++;
		}
		//３元牌が３つ揃っている
		if(countSangen == 3){
			return true;
		}else{
			return false;
		}
	}
	//TODO 天和、地和は後で考える。
/*
	static boolean checkTenhou(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}

	static boolean checkTihou(Tehai tehai, Hai addHai, Combi combi) {
		return true;
	}
*/
	
	static boolean checkSyousuushi(Tehai tehai, Hai addHai, Combi combi) {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//東が刻子
		if(checkTon(tehai,addHai,combi) == true){
			countFon++;
		}
		//南が刻子
		if(checkNan(tehai,addHai,combi) == true){
			countFon++;
		}
		//西が刻子
		if(checkSya(tehai,addHai,combi) == true){
			countFon++;
		}
		//北が刻子
		if(checkPei(tehai,addHai,combi) == true){
			countFon++;
		}

		//頭が風牌 かつ、風牌役が3つ成立
		if(((combi.atamaId & KIND_FON) != 0) && (countFon == 3)){
			return true;
		}else{
			return false;
		}
	}

	static boolean checkDaisuushi(Tehai tehai, Hai addHai, Combi combi) {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//東が刻子
		if(checkTon(tehai,addHai,combi) == true){
			countFon++;
		}
		//南が刻子
		if(checkNan(tehai,addHai,combi) == true){
			countFon++;
		}
		//西が刻子
		if(checkSya(tehai,addHai,combi) == true){
			countFon++;
		}
		//北が刻子
		if(checkPei(tehai,addHai,combi) == true){
			countFon++;
		}
			//風牌役が4つ成立
		if(countFon == 4){
			return true;
		}else{
			return false;
		}
	}

	static boolean checkTuuisou(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 
		
		//順子があるかどうか確認
		if(checkToitoi(tehai,addHai,combi) == false){
			return false;
		}
		
		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int j = 0; j < jyunTehaiLength; j++) {
			id = jyunTehai[j].getId();
			//牌が数牌
			if ((id & KIND_SHUU) != 0){
				return false;
			}
		}
		
		//明順の牌をチェック
		for(int j = 0; j < tehai.getMinshunsLength(); j++){
			checkHai = tehai.getMinshuns();
			id = checkHai[j][0].getId();
			//牌が数牌
			if ((id & KIND_SHUU) != 0){
				return false;
			}
		}
		
		//明刻の牌をチェック
		for(int j = 0; j < tehai.getMinkousLength(); j++){
			checkHai = tehai.getMinkous();
			id = checkHai[j][0].getId();
			//牌が数牌
			if ((id & KIND_SHUU) != 0){
				return false;
			}
		}
		
		//明槓の牌をチェック
		for(int j = 0; j < tehai.getMinkansLength(); j++){
			checkHai = tehai.getMinkans();
			id = checkHai[j][0].getId();
			//牌が数牌
			if ((id & KIND_SHUU) != 0){
				return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int j = 0; j < tehai.getAnkansLength(); j++){
			checkHai = tehai.getAnkans();
			id = checkHai[j][0].getId();
			//牌が数牌
			if ((id & KIND_SHUU) != 0){
				return false;
			}
		}
		
		return true;
	}

	static boolean checkChinroutou(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 
		
		//順子があるかどうか確認
		if(checkToitoi(tehai,addHai,combi) == false){
			return false;
		}
		
		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int j = 0; j < jyunTehaiLength; j++) {
			id = jyunTehai[j].getId();
			//牌が字牌ならば不成立
			if((id & KIND_TSUU) != 0){
				return false;
			}else{
				//数牌の場合1,9牌以外は不成立
				id &= KIND_MASK;
				if ((id > 1) && (id < 9)){
					return false;
				}
			}
		}
		
		//明順の牌をチェック
		for(int j = 0; j < tehai.getMinshunsLength(); j++){
			checkHai = tehai.getMinshuns();
			id = checkHai[j][0].getId();
			//牌が字牌ならば不成立
			if((id & KIND_TSUU) != 0){
				return false;
			}else{
				//数牌の場合1,9牌以外は不成立
				id &= KIND_MASK;
				if ((id > 1) && (id < 9)){
					return false;
				}
			}
		}
		
		//明刻の牌をチェック
		for(int j = 0; j < tehai.getMinkousLength(); j++){
			checkHai = tehai.getMinkous();
			id = checkHai[j][0].getId();
			//牌が字牌ならば不成立
			if((id & KIND_TSUU) != 0){
				return false;
			}else{
				//数牌の場合1,9牌以外は不成立
				id &= KIND_MASK;
				if ((id > 1) && (id < 9)){
					return false;
				}
			}
		}
		
		//明槓の牌をチェック
		for(int j = 0; j < tehai.getMinkansLength(); j++){
			checkHai = tehai.getMinkans();
			id = checkHai[j][0].getId();
			//牌が字牌ならば不成立
			if((id & KIND_TSUU) != 0){
				return false;
			}else{
				//数牌の場合1,9牌以外は不成立
				id &= KIND_MASK;
				if ((id > 1) && (id < 9)){
					return false;
				}
			}
		}
		
		//暗槓の牌をチェック
		for(int j = 0; j < tehai.getAnkansLength(); j++){
			checkHai = tehai.getAnkans();
			id = checkHai[j][0].getId();
			//牌が字牌ならば不成立
			if((id & KIND_TSUU) != 0){
				return false;
			}else{
				//数牌の場合1,9牌以外は不成立
				id &= KIND_MASK;
				if ((id > 1) && (id < 9)){
					return false;
				}
			}
		}
		
		return true;	}

	static boolean checkRyuuisou(Tehai tehai, Hai addHai, Combi combi) {
		int checkId[] = {KIND_SOU+2,KIND_SOU+3,KIND_SOU+4,KIND_SOU+6,KIND_SOU+8,KIND_HATU};
		int id;
		boolean ryuuisouflg = false;
		Hai[] jyunTehai = tehai.getJyunTehai();
		Hai checkHai[][]; 

		//純手牌をチェック
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getId();
			ryuuisouflg = false;
			for(int j = 0 ; j < checkId.length ; j++){
				//緑一色に使用できる牌だった
				if(id == checkId[j]){
					ryuuisouflg = true;
				}
			}
			//該当する牌ではなかった
			if(ryuuisouflg == false){
				return false;
			}
		}
		
		//明順の牌をチェック
		for(int i = 0; i < tehai.getMinshunsLength(); i++){
			checkHai = tehai.getMinshuns();
			id = checkHai[i][0].getId();
			//索子の2,3,4以外の順子があった場合不成立
			if (id != (KIND_SOU + 2)){
				return false;
			}
		}
		
		//明刻の牌をチェック
		for(int i = 0; i < tehai.getMinkousLength(); i++){
			checkHai = tehai.getMinkous();
			id = checkHai[i][0].getId();
			ryuuisouflg = false;
			for(int j = 0 ; j < checkId.length ; j++){
				//緑一色に使用できる牌だった
				if(id == checkId[j]){
					ryuuisouflg = true;
				}
			}
			//該当する牌ではなかった
			if(ryuuisouflg == false){
				return false;
			}
		}
		
		//明槓の牌をチェック
		for(int i = 0; i < tehai.getMinkansLength(); i++){
			checkHai = tehai.getMinkans();
			id = checkHai[i][0].getId();
			ryuuisouflg = false;
			for(int j = 0 ; j < checkId.length ; j++){
				//緑一色に使用できる牌だった
				if(id == checkId[j]){
					ryuuisouflg = true;
				}
			}
			//該当する牌ではなかった
			if(ryuuisouflg == false){
				return false;
			}
		}
		
		//暗槓の牌をチェック
		for(int i = 0; i < tehai.getAnkansLength(); i++){
			checkHai = tehai.getAnkans();
			id = checkHai[i][0].getId();
			if ((id & KIND_SHUU) == 0)
				ryuuisouflg = false;
			for(int j = 0 ; j < checkId.length ; j++){
				//緑一色に使用できる牌だった
				if(id == checkId[j]){
					ryuuisouflg = true;
				}
			}
			//該当する牌ではなかった
			if(ryuuisouflg == false){
				return false;
			}
		}
		
		//条件に該当した
		return true;
	}

	static boolean checkCyuurennpoutou(Tehai tehai, Hai addHai, Combi combi) {
		int id;
		
		//牌の数を調べるための配列 (0番地は使用しない）
		int countNumber[] = {0,0,0,0,0,0,0,0,0,0};
		Hai checkHai[] = new Hai[JYUNTEHAI_MAX];
		
		//鳴きがある場合は成立しない
		if(tehai.getJyunTehaiLength() < JYUNTEHAI_MAX){
			return false;
		}
		//手牌が清一になっていない場合も成立しない
		if(checkTinitu(tehai,addHai,combi) == false){
			return false;
		}
		
		//手牌をコピーする
		checkHai = tehai.getJyunTehai();
		
		//手牌にある牌の番号を調べる
		for(int i = 0 ; i < tehai.getJyunTehaiLength() ; i++){
			id = checkHai[i].getId();
			//数字をチェックする
			id &= KIND_MASK;
			//数字-1の番号をインクリメントする
			countNumber[id]++;
		}
		
		//九蓮宝燈になっているか調べる（1と9が３枚以上 2〜8が１枚以上)
		if(( countNumber[1] >= 3)
		&& ( countNumber[2] >= 1)
		&& ( countNumber[3] >= 1)
		&& ( countNumber[4] >= 1)
		&& ( countNumber[5] >= 1)
		&& ( countNumber[6] >= 1)
		&& ( countNumber[7] >= 1)
		&& ( countNumber[8] >= 1)
		&& ( countNumber[9] >= 3)){
			return true;
		}
		
		return false;
	}
}
